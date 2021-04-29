package me.sullivan.glasslang.interpreter.primitives.types;

import me.sullivan.glasslang.interpreter.runtime.Context;

import java.util.HashMap;

public class TypePrimitive extends Primitive<Type>
{
    public TypePrimitive(Type value, Context context)
    {
        super(value, Type.TYPE, context, new HashMap<>());
    }

    public BooleanPrimitive is(Primitive<?> other)
    {
        return new BooleanPrimitive(other.getType() == value, context);
    }
}
