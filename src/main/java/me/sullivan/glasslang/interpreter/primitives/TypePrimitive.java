package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.runtime.Context;

public class TypePrimitive extends Primitive<Type>
{
    public TypePrimitive(Type value, Context context)
    {
        super(value, Type.TYPE, context);
    }

    public BooleanPrimitive is(Primitive<?> other)
    {
        return new BooleanPrimitive(other.getType() == value, context);
    }
}
