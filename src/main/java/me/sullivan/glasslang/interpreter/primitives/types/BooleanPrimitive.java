package me.sullivan.glasslang.interpreter.primitives.types;

import me.sullivan.glasslang.interpreter.runtime.Context;

import java.util.HashMap;

public class BooleanPrimitive extends Primitive<Boolean>
{
    public BooleanPrimitive(Boolean value, Context context)
    {
        super(value, Type.BOOLEAN, context, new HashMap<>());
    }

    @Override
    public boolean isTrue()
    {
        return value;
    }

    @Override
    public BooleanPrimitive not()
    {
        return new BooleanPrimitive(!value, context);
    }

    @Override
    public BooleanPrimitive and(Primitive<?> other)
    {
        BooleanPrimitive otherValue = other.getValue(Type.BOOLEAN);
        return new BooleanPrimitive(value && otherValue.getValue(), context);
    }

    @Override
    public BooleanPrimitive or(Primitive<?> other)
    {
        BooleanPrimitive otherValue = other.getValue(Type.BOOLEAN);
        return new BooleanPrimitive(value || otherValue.getValue(), context);
    }

    @Override
    public Primitive<?> add(Primitive<?> other)
    {
        StringPrimitive otherValue = other.getValue(Type.STRING);
        return new StringPrimitive(value + otherValue.getValue(), context);
    }
}
