package me.sullivan.glasslang.lexer.errors;

import java.text.MessageFormat;

import me.sullivan.glasslang.lexer.Position;

public class InvalidCharError extends Error {

	private static final long serialVersionUID = 3268750033881002602L;

	public InvalidCharError(String message, Position pos)
	{
		super(MessageFormat.format("Invalid Character, \"{0}\" at line {1} column {2}", message, pos.getLine(), pos.getColumn()));
	}
}
