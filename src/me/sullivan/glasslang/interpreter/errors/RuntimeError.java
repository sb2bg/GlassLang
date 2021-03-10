package me.sullivan.glasslang.interpreter.errors;

import java.text.MessageFormat;

public class RuntimeError extends Error {

	private static final long serialVersionUID = -7298453663805779774L;
	
	public RuntimeError(String message)
	{
		super(MessageFormat.format("Runtime error, \"{0}\"", message));
	}
}
