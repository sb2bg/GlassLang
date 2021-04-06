package me.sullivan.glasslang.interpreter;

import java.util.List;
import java.util.Map;

import me.sullivan.glasslang.interpreter.primitives.FunctionPrimitive;
import me.sullivan.glasslang.interpreter.primitives.NumberPrimitive;
import me.sullivan.glasslang.interpreter.primitives.Primitive;
import me.sullivan.glasslang.interpreter.primitives.StringPrimitive;
import me.sullivan.glasslang.interpreter.primitives.Type;
import me.sullivan.glasslang.interpreter.primitives.VoidPrimitive;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.lexer.token.TokenType;
import me.sullivan.glasslang.parser.nodes.AssignmentNode;
import me.sullivan.glasslang.parser.nodes.BinOpNode;
import me.sullivan.glasslang.parser.nodes.CallNode;
import me.sullivan.glasslang.parser.nodes.ForNode;
import me.sullivan.glasslang.parser.nodes.FunctionDefinitonNode;
import me.sullivan.glasslang.parser.nodes.IfNode;
import me.sullivan.glasslang.parser.nodes.Node;
import me.sullivan.glasslang.parser.nodes.NumberNode;
import me.sullivan.glasslang.parser.nodes.StringNode;
import me.sullivan.glasslang.parser.nodes.UnaryOpNode;
import me.sullivan.glasslang.parser.nodes.VariableNode;
import me.sullivan.glasslang.parser.nodes.WhileNode;

public class Interpreter {

	private Context context;

	public Interpreter(Context context)
	{
		this.context = context;
	}

	public Context currentContext()
	{
		return context;
	}

	public Primitive<?> visitNode(Node node)
	{
		return node.visitor(this);
	}

	public Primitive<?> visitBinOp(BinOpNode node)
	{
		Primitive<?> left = visitNode(node.getLeft());
		Primitive<?> right = visitNode(node.getRight());

		switch (node.getValue().getType())
		{
		case PLUS: return left.add(right);
		case MINUS: return left.min(right);
		case DIVIDE: return left.div(right);
		case TIMES: return left.mul(right);
		case POWER: return left.pow(right);
		case GREATER: return left.greater(right);
		case GREATER_EQUAL: return left.greaterEqual(right);
		case LESS: return left.less(right);
		case LESS_EQUAL: return left.lessEqual(right);
		case EQUAL_OP: return left.equal(right);
		case NOT_EQUAL: return left.notEqual(right);
		case AND: return left.and(right);
		case OR: return left.or(right);
		default: return null;
		}
	}

	public Primitive<?> visitNumberNode(NumberNode node)
	{
		return new NumberPrimitive(node.getValue(), context);
	}

	public Primitive<?> visitUnaryOpNode(UnaryOpNode node)
	{
		Primitive<?> number = visitNode(node.getNode());

		if (node.getToken().getType() == TokenType.MINUS)
		{
			number = number.mul(new NumberPrimitive(-1, context));
		}
		else if (node.getToken().getType() == TokenType.NOT)
		{
			number = number.not();
		}

		return number;
	}

	public Primitive<?> visitVariableNode(VariableNode node)
	{
		return context.getTable().get(node.getToken().getValue());
	}

	public Primitive<?> visitAssignmentNode(AssignmentNode node)
	{
		return context.getTable().set(node.getToken().getValue(), visitNode(node.getValue()));
	}

	public Primitive<?> visitIfNode(IfNode node)
	{
		Map<Node, Node> statements = node.getValue();

		for (Node key : statements.keySet())
		{
			Node value = statements.get(key);

			Primitive<?> condition = visitNode(key);

			if (condition.isTrue())
			{
				return visitNode(value);
			}

			if (node.getElseCase() != null)
			{
				return visitNode(node.getElseCase());
			}
		}

		return new VoidPrimitive();
	}

	private interface Condition
	{
		boolean condition(double i);
	}

	public Primitive<?> visitForNode(ForNode node)
	{
		boolean pre = context.getTable().contains(node.getValue());
		NumberPrimitive startVal = visitNode(node.getStartValue()).getValue(Type.NUMBER);
		NumberPrimitive endVal = visitNode(node.getEndValue()).getValue(Type.NUMBER);
		NumberPrimitive step = new NumberPrimitive(1, context);

		if (node.getStep() != null)
		{
			step = visitNode(node.getStep()).getValue(Type.NUMBER);
		}

		double i = startVal.getValue();

		Condition condition = i >= 0 ? val -> val <= endVal.getValue() : val -> val >= endVal.getValue();

		while (condition.condition(i))
		{
			context.getTable().set(node.getValue(), new NumberPrimitive(i, context));
			i += step.getValue();

			visitNode(node.getEval());
		}

		if (!pre)
		{
			context.getTable().delete(node.getValue());
		}

		return new VoidPrimitive();
	}

	public Primitive<?> visitWhileNode(WhileNode node)
	{
		Primitive<?> condition = visitNode(node.getCondition());
		
		while (condition.isTrue())
		{
			visitNode(node.getValue());
			condition = visitNode(node.getCondition());
		}

		return new VoidPrimitive();
	}

	public Primitive<?> visitFuncDefinitionNode(FunctionDefinitonNode node)
	{
		String name = node.getToken() != null ? node.getToken().getValue() : null;
		Node bodyNode = node.getBody();
		List<Token> args = node.getArgs();

		FunctionPrimitive function = new FunctionPrimitive(name, bodyNode, args, context);

		if (name != null)
		{
			context.getTable().set(name, function);
		}

		return function;
	}

	public Primitive<?> visitCallNode(CallNode node)
	{
		return visitNode(node.nodeToCall()).call(node.getArgNodes());
	}

	public Primitive<?> visitStringNode(StringNode stringNode)
	{
		return new StringPrimitive(stringNode.getValue(), context);
	}
}
