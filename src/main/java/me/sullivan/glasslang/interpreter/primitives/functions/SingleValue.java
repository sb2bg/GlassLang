package me.sullivan.glasslang.interpreter.primitives.functions;

import me.sullivan.glasslang.interpreter.primitives.Primitive;

public class SingleValue extends Value
{
    public SingleValue(String var)
    {
        super(var, ValueType.SINGLE);
    }
}
