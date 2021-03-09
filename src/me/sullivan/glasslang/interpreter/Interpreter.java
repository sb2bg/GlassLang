package me.sullivan.glasslang.interpreter;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
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

	public Object visitNode(Node node, Context context)
	{
		try
		{
			if (node instanceof BinOpNode)
			{
				return visitBinOp((BinOpNode)node, context);
			}
			else if (node instanceof NumberNode)
			{
				return visitNumberNode((NumberNode)node, context);
			}
			else if (node instanceof UnaryOpNode)
			{
				return visitUnaryOpNode((UnaryOpNode)node, context);
			}
			else if (node instanceof VariableNode)
			{
				return visitVariableNode((VariableNode)node, context);
			}
			else if (node instanceof AssignmentNode)
			{
				return visitAssignmentNode((AssignmentNode)node, context);
			}
		}
		catch (ClassCastException e)
		{
			throw new RuntimeError("This is a language interpreting error... You did nothing wrong, I swear!");
		}
		
		throw new RuntimeError("This is a language interpreting error... You did nothing wrong, I swear!");
	}

	private NumberPrimitive visitBinOp(BinOpNode node, Context context)
	{
		NumberPrimitive left = (NumberPrimitive)visitNode(node.getLeft(), context);
		NumberPrimitive right = (NumberPrimitive)visitNode(node.getRight(), context);

		switch (node.getValue().getType())
		{
		case PLUS: return left.add(right);
		case MINUS: return left.min(right);
		case DIVIDE: return left.div(right);
		case TIMES: return left.mul(right);
		default: return null;
		}
	}

	private NumberPrimitive visitNumberNode(NumberNode node, Context context)
	{
		return new NumberPrimitive(node.getValue());
	}

	private NumberPrimitive visitUnaryOpNode(UnaryOpNode node, Context context)
	{
		NumberPrimitive number = (NumberPrimitive)visitNode(node.getNode(), context);
		
		if (node.getToken().getType() == TokenType.MINUS)
		{
			number = number.mul(new NumberPrimitive(-1));
		}
		
		return number;
	}

	private Object visitVariableNode(VariableNode node, Context context)
	{
		return null;
	}

	private Object visitAssignmentNode(AssignmentNode node, Context context)
	{
		return null;
	}
}
