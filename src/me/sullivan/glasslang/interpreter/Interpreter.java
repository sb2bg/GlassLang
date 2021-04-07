package me.sullivan.glasslang.interpreter;

import java.util.List;
import java.util.Map;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.primitives.*;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.lexer.token.TokenType;
import me.sullivan.glasslang.parser.nodes.*;

public class Interpreter {

	private final Context context;

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

		return switch (node.getValue().getType()) {
			case PLUS -> left.add(right);
			case MINUS -> left.min(right);
			case DIVIDE -> left.div(right);
			case TIMES -> left.mul(right);
			case POWER -> left.pow(right);
			case GREATER -> left.greater(right);
			case GREATER_EQUAL -> left.greaterEqual(right);
			case LESS -> left.less(right);
			case LESS_EQUAL -> left.lessEqual(right);
			case EQUAL_OP -> left.equal(right);
			case NOT_EQUAL -> left.notEqual(right);
			case AND -> left.and(right);
			case OR -> left.or(right);
			default -> null;
		};
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
		Primitive<?> start = visitNode(node.getStartValue());
		Primitive<?> end = visitNode(node.getEndValue());

		if (start.getType() != Type.NUMBER)
		{
			throw new RuntimeError("Start value expected to be number");
		}
		
		if (end.getType() != Type.NUMBER)
		{
			throw new RuntimeError("End value expected to be number");
		}

		NumberPrimitive startVal = start.getValue(Type.NUMBER);
		NumberPrimitive endVal = end.getValue(Type.NUMBER);
		NumberPrimitive stepVal = new NumberPrimitive(1, context);

		if (node.getStep() != null)
		{
			Primitive<?> step = visitNode(node.getStep());
			
			if (step.getType() != Type.NUMBER)
			{
				throw new RuntimeError("Step value expected to be number");
			}
			
			stepVal = visitNode(node.getStep()).getValue(Type.NUMBER);
		}

		double i = startVal.getValue();

		Condition condition = i >= 0 ? val -> val <= endVal.getValue() : val -> val >= endVal.getValue();

		while (condition.condition(i))
		{
			context.getTable().set(node.getValue(), new NumberPrimitive(i, context));
			i += stepVal.getValue();

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

	public Primitive<?> visitListNode(ListNode node)
	{
		return new ListPrimitive(node.getValue(), context);
	}
}
