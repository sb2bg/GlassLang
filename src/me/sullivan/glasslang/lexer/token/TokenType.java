package me.sullivan.glasslang.lexer.token;

public enum TokenType {

	PLUS_EQUALS ("PLUS EQUALS", "'+='"), MINUS_EQUALS ("MINUS EQUALS", "'-='"), TIMES_EQUALS ("TIMES EQUALS", "'*='"), 
	DIVIDED_EQUALS ("DIVIDED EQUALS", "'/='"), NUMBER ("NUMBER", "number"), PLUS ("PLUS", "'+'"), MINUS ("MINUS", "'-'"), 
	TIMES ("TIMES", "'*'"), DIVIDE ("DIVIDE", "'/'"), STRING ("STRING", "string"), IDENTIFIER ("IDENTIFIER", "identifier"), 
	FOR ("FOR", "'for'"), EQUALS ("EQUALS", "'='"),NOT_EQUAL ("NOT EQUAL", "'!='"), LPAREN ("LPAREN", "'('"), 
	RPAREN ("RPAREN", "')'"), LESS ("LESS THAN", "'<'"), GREATER ("GREATER THAN", "'>'"), LESS_EQUAL ("LESS THAN EQUAL TO", "'<='"), 
	POWER("POW", "'**'"), IF ("IF", "'if'"), RETURN ("RETURN", "'return'"), WHILE ("WHILE", "'while'"), AT_SIGN ("AT SIGN", "'@'"), 
	EQUAL_OP ("EQUAL OPERATOR", "'=='"), LBRACKET ("LEFT SQUARE BRACKET", "'['"), RBRACKET ("RIGHT SQUARE BRACKET", "']'"),
	GREATER_EQUAL ("GREATER THAN EQUAL TO", "'>='"), MONEY_SIGN ("MONEY SIGN", "'$'"), COMMA ("COMMA", ",'"), STEP ("STEP", "'step'"),
	AND ("AND", "'and'"), OR ("OR", "'or'"), NOT ("NOT", "'not'"), ELSE ("ELSE", "'else'"), END ("END", "'end'"), ELIF ("ELIF", "'elif'"), TO ("TO", "'to'"),
	LAMBDA ("LAMBDA", "'=>'"), MOD ("MODULUS", "'%'"),
	
	EOL ("EndOfLine", null), EOF ("EndOfFile", null);
	
	private final String name;
	private final String rep;
	
	TokenType(final String name, final String rep)
	{
		this.name = name;
		this.rep = rep;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getRep()
	{
		return rep;
	}
}
