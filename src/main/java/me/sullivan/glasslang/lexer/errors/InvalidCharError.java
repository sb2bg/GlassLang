package me.sullivan.glasslang.lexer.errors;

import me.sullivan.glasslang.lexer.Position;

import java.io.Serial;
import java.text.MessageFormat;

public class InvalidCharError extends Error
{
    @Serial
    private static final long serialVersionUID = 3268750033881002602L;

    public InvalidCharError(String message, Position pos)
    {
        super(MessageFormat.format("Invalid Character, \"{0}\" at line {1} column {2}", message, pos.getLine(), pos.getColumn()));
    }
}
