package me.sullivan.glasslang.lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.sullivan.glasslang.lexer.errors.InvalidCharError;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.lexer.token.TokenType;

public class Lexer {

	private static final char NUL = Character.MIN_VALUE;
	private static final Map<String, TokenType> TOKENS = new HashMap<>();
	static
	{
		TOKENS.put("+", TokenType.PLUS);
		TOKENS.put("-", TokenType.MINUS);
		TOKENS.put("*", TokenType.TIMES);
		TOKENS.put("/", TokenType.DIVIDE);
		TOKENS.put("**", TokenType.POWER);
		TOKENS.put("=", TokenType.EQUALS);
		TOKENS.put("(", TokenType.LPAREN);
		TOKENS.put(")", TokenType.RPAREN);
		TOKENS.put("<", TokenType.LESS);
		TOKENS.put(">", TokenType.GREATER);
		TOKENS.put("<=", TokenType.LESS_EQUAL);
		TOKENS.put(">=", TokenType.GREATER_EQUAL);
		TOKENS.put("<>", TokenType.NOT_EQUAL);
		TOKENS.put(",", TokenType.COMMA);
		TOKENS.put("+=", TokenType.PLUS_EQUALS);
		TOKENS.put("-=", TokenType.MINUS_EQUALS);
		TOKENS.put("*=", TokenType.TIMES_EQUALS);
		TOKENS.put("/=", TokenType.DIVIDED_EQUALS);
		TOKENS.put("!=", TokenType.NOT_EQUAL);
		TOKENS.put("==", TokenType.EQUAL_OP);
		
		TOKENS.put("func", TokenType.FUNC);
		TOKENS.put("while", TokenType.WHILE);
		TOKENS.put("$", TokenType.MONEY_SIGN);
		TOKENS.put("@", TokenType.AT_SIGN);
		TOKENS.put("return", TokenType.RETURN);
		TOKENS.put("if", TokenType.IF);
		TOKENS.put("for", TokenType.FOR);
	}

	private final String input;
	private final Position pos = new Position(0, 1, 0);
	private char current;

	public Lexer (final String input)
	{
		this.input = input.strip();

		if (input != null && !input.isBlank())
		{
			this.current = this.input.charAt(pos.getIndex());
		}
	}

	public List<Token> lex()
	{		
		List<Token> tokens = new ArrayList<>();

		if (input == null || input.isBlank())
		{
			tokens.add(new Token(TokenType.EOL));
			return tokens;
		}

		while (current != NUL)
		{
			if (isWhiteSpace())
			{
				advance();
			}
			else if (isNumber())
			{
				tokens.add(consumeNumber());
			}
			else if (isOperator())
			{
				tokens.add(consumeOperator());
			}
			else if (isSingleConsume())
			{
				tokens.add(new Token(TOKENS.get(Character.toString(current))));
				advance();
			}
			else if (isString())
			{
				tokens.add(consumeString());
			}
			else
			{
				tokens.add(consumeWord());
			}
		}

		tokens.add(new Token(TokenType.EOL));
		return tokens;
	}

	private Token consumeOperator()
	{
		StringBuilder buffer = new StringBuilder();

		while (current != NUL && isOperator())
		{
			buffer.append(current);
			advance();
		}

		if (TOKENS.containsKey(buffer.toString()))
		{
			return new Token(TOKENS.get(buffer.toString()));
		}

		throw new InvalidCharError("Invalid char " + current, pos);
	}

	public void advance()
	{
		pos.advance(current);
		current = pos.getIndex() != input.length() ? input.charAt(pos.getIndex()) : NUL;
	}

	private Token consumeString()
	{
		advance();
		StringBuilder buffer = new StringBuilder();

		while (current != NUL && !isString())
		{
			buffer.append(current);
			advance();
		}

		if (current == NUL)
		{
			throw new InvalidCharError("Unclosed string", pos);
		}

		advance();
		return new Token(TokenType.STRING, buffer.toString());
	}
	
	private String numString()
	{
		StringBuilder buffer = new StringBuilder();
		boolean decimal = false;

		while (current != NUL && (isNumber() || isPeriod()))
		{
			if (isPeriod() && decimal)
			{
				throw new InvalidCharError("Invalid char " + current, pos);
			}
			else if (isPeriod())
			{
				decimal = true;
			}

			buffer.append(current);
			advance();
		}

		String num = buffer.toString();

		if (num.endsWith("."))
		{
			throw new InvalidCharError("Invalid char " + current, pos);
		}

		return num;
	}

	private Token consumeNumber()
	{
		return new Token(TokenType.NUMBER, numString());
	}

	private Token consumeWord()
	{
		StringBuilder buffer = new StringBuilder();

		while (current != NUL && (isLetter() || isNumber() || isUnderscore()))
		{
			buffer.append(current);
			advance();
		}

		String tok = buffer.toString();

		if (TOKENS.containsKey(tok))
		{
			return new Token(TOKENS.get(tok));
		}

		if (tok.isEmpty())
		{
			throw new InvalidCharError("Unknown character " + current, pos);
		}

		return new Token(TokenType.IDENTIFIER, tok);
	}

	private static final String WHITESPACE = " \t";

	private boolean isWhiteSpace()
	{
		return WHITESPACE.contains(Character.toString(current));
	}

	private boolean isNumber()
	{
		return Character.isDigit(current);
	}

	private boolean isLetter()
	{
		return Character.isLetter(current);
	}

	private boolean isPeriod()
	{
		return current == '.';
	}
	
	private boolean isString()
	{
		return current == '"';
	}

	private boolean isOperator()
	{
		return "+-*/<>=!".contains(Character.toString(current));
	}
	
	private boolean isSingleConsume()
	{
		return ",()$@".contains(Character.toString(current));
	}
	
	private boolean isUnderscore()
	{
		return "_".contains(Character.toString(current));
	}
}
