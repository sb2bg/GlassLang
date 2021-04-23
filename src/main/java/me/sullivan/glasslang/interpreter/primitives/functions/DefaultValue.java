package me.sullivan.glasslang.interpreter.primitives.functions;

import me.sullivan.glasslang.interpreter.primitives.Primitive;

public class DefaultValue extends Value
{
    private final Primitive<?> defaultVar;

    public DefaultValue(String var, Primitive<?> defaultVar)
    {
        super(var, ValueType.DEFAULT);
        this.defaultVar = defaultVar;
    }

    @Override
    public Primitive<?> getDefault()
    {
        return defaultVar;
    }
}
