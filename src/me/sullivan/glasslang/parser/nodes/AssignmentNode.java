package me.sullivan.glasslang.parser.nodes;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitves.NumberPrimitive;
import me.sullivan.glasslang.lexer.token.Token;

public class AssignmentNode extends Node {

	private Node node;
	
	public AssignmentNode(Token token, Node node)
	{
		super(token);
		this.node = node;
	}

	@Override
	public Node getValue()
	{
		return node;
	}

	@Override
	public String toString()
	{
		return "Assignment(" + token.getValue() + ", " + getValue() + ")";
	}

	@Override
	public NumberPrimitive visitor(Interpreter interpreter)
	{
		return interpreter.visitAssignmentNode(this);
	}
}
