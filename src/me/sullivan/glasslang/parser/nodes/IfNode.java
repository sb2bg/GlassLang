package me.sullivan.glasslang.parser.nodes;

import java.util.Map;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.Primitive;
import me.sullivan.glasslang.lexer.token.Token;

public class IfNode extends Node {

	private Map<Node, Node> cases;
	private Node elseCase;
	
	public IfNode(Token token, Map<Node, Node> cases, Node elseCase)
	{
		super(token);
		this.cases = cases;
		this.elseCase = elseCase;
	}

	@Override
	public Map<Node, Node> getValue()
	{
		return cases;
	}
	
	public Node getElseCase()
	{
		return elseCase;
	}

	@Override
	public String toString()
	{
		return "If(" + cases + ") else => " + elseCase;
	}

	@Override
	public Primitive<?> visitor(Interpreter interpreter)
	{
		return interpreter.visitIfNode(this);
	}
}
