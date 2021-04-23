package me.sullivan.glasslang.interpreter.primitives.functions;

import me.sullivan.glasslang.interpreter.primitives.Primitive;

public abstract class Value
{
    private final String var;
    private final ValueType type;

    public Value(String var, ValueType type)
    {
        this.var = var;
        this.type = type;
    }

    public String getVar()
    {
        return var;
    }

    public Primitive<?> getDefault()
    {
        return null;
    }

    public ValueType getType()
    {
        return type;
    }
}
