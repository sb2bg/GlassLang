package me.sullivan.glasslang.parser.errors;

import me.sullivan.glasslang.lexer.token.TokenType;

public class InvalidSyntaxError extends Error {

	private static final long serialVersionUID = -4042750522507292037L;

	public InvalidSyntaxError(TokenType[] expected)
	{
		super("Invalid Syntax, expected " + stringify(expected));
	}
	
	private static final String stringify(TokenType[] types)
	{
		StringBuilder result = new StringBuilder();
		
		for (TokenType type : types)
		{
			result.append(type.getRep() + ',');
		}
		
		return result.toString();
	}
}
