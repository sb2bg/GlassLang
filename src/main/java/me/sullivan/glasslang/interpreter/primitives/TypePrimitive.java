package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.parser.nodes.Node;

import java.util.HashMap;
import java.util.List;

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
