package me.sullivan.glasslang.parser.nodes;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.Primitive;
import me.sullivan.glasslang.lexer.token.Token;

public class NumberNode extends Node {

	public NumberNode(Token token)
	{
		super(token);
	}

	@Override
	public Double getValue()
	{
		return Double.parseDouble(token.getValue());
	}

	@Override
	public String toString()
	{
		return "Number(" + getValue() + ")";
	}

	@Override
	public Primitive<?> visitor(Interpreter interpreter)
	{
		return interpreter.visitNumberNode(this);
	}
}
