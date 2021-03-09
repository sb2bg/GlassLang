package me.sullivan.glasslang.lexer.token;

import java.text.MessageFormat;

public class Token {

	private TokenType tokenType;
	private String value;

	public Token(TokenType type, String value)
	{
		this.tokenType = type;
		this.value = value;
	}
	
	public Token(TokenType type) 
	{
		this.tokenType = type;
	}

	public String getValue()
	{
		return value;
	}
	
	public TokenType getType()
	{
		return tokenType;
	}

	@Override
	public String toString()
	{
		return tokenType.getName() + (value == null ? "" : MessageFormat.format("({0})", value));
	}
}
