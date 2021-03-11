package me.sullivan.glasslang.lexer.token;

public enum TokenType {

	PLUS_EQUALS ("PLUS EQUALS"), MINUS_EQUALS ("MINUS EQUALS"), TIMES_EQUALS ("TIMES EQUALS"), 
	DIVIDED_EQUALS ("DIVIDED EQUALS"), NUMBER ("NUMBER"), PLUS ("PLUS"), MINUS ("MINUS"), 
	TIMES ("TIMES"), DIVIDE ("DIVIDE"), STRING ("STRING"), IDENTIFIER ("IDENTIFIER"), FOR ("FOR"),
	EQUALS ("EQUALS"),NOT_EQUAL ("NOT EQUAL"), LPAREN ("LPAREN"), RPAREN ("RPAREN"), 
	LESS ("LESS THAN"), GREATER ("GREATER THAN"), LESS_EQUAL ("LESS THAN EQUAL TO"), POWER("POW"),
	IF ("IF"), RETURN ("RETURN"), WHILE ("WHILE"), AT_SIGN ("AT SIGN"), EQUAL_OP ("EQUAL OPERATOR"),
	GREATER_EQUAL ("GREATER THAN EQUAL TO"), MONEY_SIGN ("MONEY SIGN"), FUNC ("FUNC"), COMMA (","),
	
	EOL ("EndOfLine"), EOF ("EndOfFile");
	
	private final String name;
	
	private TokenType(final String name) 
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
}
