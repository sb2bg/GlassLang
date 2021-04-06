package me.sullivan.glasslang.parser.nodes;

import java.util.List;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.Primitive;
import me.sullivan.glasslang.lexer.token.Token;

public class FunctionDefinitonNode extends Node {

	private final List<Token> args;
	private final Node body;
	
	public FunctionDefinitonNode(Token token, List<Token> args, Node body) 
	{
		super(token);
		this.args = args;
		this.body = body;
	}

	@Override
	public Object getValue() 
	{
		return token.getValue();
	}

	@Override
	public String toString() 
	{
		return "Function(" + (token != null ? token.getValue() : "anonymous-func") + ", " + args + " => " + body;
	}

	@Override
	public Primitive<?> visitor(Interpreter interpreter) 
	{
		return interpreter.visitFuncDefinitionNode(this);
	}
	
	public List<Token> getArgs()
	{
		return args;
	}
	
	public Node getBody()
	{
		return body;
	}
}
