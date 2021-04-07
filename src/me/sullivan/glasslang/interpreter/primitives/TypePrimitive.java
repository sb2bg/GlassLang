package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.runtime.Context;

public class TypePrimitive extends Primitive<Primitive<?>>
{
    public TypePrimitive(Primitive<?> value, Type type, Context context)
    {
        super(value, type, context);
    }
}
