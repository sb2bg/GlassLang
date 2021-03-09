package me.sullivan.glasslang.parser.errors;

import java.text.MessageFormat;

public class InvalidSyntaxError extends Error {

	private static final long serialVersionUID = -4042750522507292037L;

	public InvalidSyntaxError(String message)
	{
		super(MessageFormat.format("Invalid Syntax, \"{0}\"", message));
	}
}
