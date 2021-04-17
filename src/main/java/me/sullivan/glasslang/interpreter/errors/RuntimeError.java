package me.sullivan.glasslang.interpreter.errors;

import me.sullivan.glasslang.interpreter.runtime.Context;

import java.io.Serial;
import java.text.MessageFormat;

public class RuntimeError extends Error
{
    @Serial
    private static final long serialVersionUID = -7298453663805779774L;

    public RuntimeError(String message, Context context)
    {
        super(MessageFormat.format("Runtime error, \"{0}\". {1}", message, context));
    }
}
