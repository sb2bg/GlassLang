package me.sullivan.glasslang.parser.nodes;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.Primitive;
import me.sullivan.glasslang.lexer.token.Token;

public class UnaryOpNode extends Node {

	private Node node;
	
	public UnaryOpNode(Token token, Node node)
	{
		super(token);
		this.node = node;
	}

	@Override
	public Token getValue()
	{
		return token;
	}

	@Override
	public String toString()
	{
		return "UnaryOp(" + getValue().getType() + ", " + node + ")";
	}

	public Node getNode()
	{
		return node;
	}

	@Override
	public Primitive<?> visitor(Interpreter interpreter)
	{
		return interpreter.visitUnaryOpNode(this);
	}
}
