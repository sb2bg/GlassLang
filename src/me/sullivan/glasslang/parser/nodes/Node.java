package me.sullivan.glasslang.parser.nodes;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.Primitive;
import me.sullivan.glasslang.lexer.token.Token;

public abstract class Node {

	protected Token token;
	
	public Node(Token token)
	{
		this.token = token;
	}
	
	public Token getToken()
	{
		return token;
	}
	
    public abstract Object getValue();

    public abstract String toString();
    
    public abstract Primitive<?> visitor(Interpreter interpreter);
}
