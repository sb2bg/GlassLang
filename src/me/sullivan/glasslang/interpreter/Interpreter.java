package me.sullivan.glasslang.interpreter;

import me.sullivan.glasslang.interpreter.primitves.NumberPrimitive;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.lexer.token.TokenType;
import me.sullivan.glasslang.parser.nodes.AssignmentNode;
import me.sullivan.glasslang.parser.nodes.BinOpNode;
import me.sullivan.glasslang.parser.nodes.Node;
import me.sullivan.glasslang.parser.nodes.NumberNode;
import me.sullivan.glasslang.parser.nodes.UnaryOpNode;
import me.sullivan.glasslang.parser.nodes.VariableNode;

public class Interpreter {

	private Context context;

	public Interpreter(Context context)
	{
		this.context = context;
	}

	public NumberPrimitive visitNode(Node node)
	{

		return node.visitor(this);
	}

	public NumberPrimitive visitBinOp(BinOpNode node)
	{
		NumberPrimitive left = visitNode(node.getLeft());
		NumberPrimitive right = visitNode(node.getRight());

		switch (node.getValue().getType())
		{
		case PLUS: return left.add(right);
		case MINUS: return left.min(right);
		case DIVIDE: return left.div(right);
		case TIMES: return left.mul(right);
		case POWER: return left.pow(right);
		default: return null;
		}
	}

	public NumberPrimitive visitNumberNode(NumberNode node)
	{
		return new NumberPrimitive(node.getValue());
	}

	public NumberPrimitive visitUnaryOpNode(UnaryOpNode node)
	{
		NumberPrimitive number = visitNode(node.getNode());

		if (node.getToken().getType() == TokenType.MINUS)
		{
			number = number.mul(new NumberPrimitive(-1));
		}

		return number;
	}

	public NumberPrimitive visitVariableNode(VariableNode node)
	{
		return context.getTable().get(node.getToken().getValue());
	}

	public NumberPrimitive visitAssignmentNode(AssignmentNode node)
	{
		return context.getTable().set(node.getToken().getValue(), visitNode(node.getValue()), false);

	}
}
