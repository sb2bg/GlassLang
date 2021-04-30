package me.sullivan.glasslang.interpreter.primitives.types;

import java.util.HashMap;

public class VoidPrimitive extends Primitive<Void>
{
    public VoidPrimitive()
    {
        super(null, Type.VOID, null, new HashMap<>());
    }

    @Override
    public Primitive<?> add(Primitive<?> other)
    {
        if (other.getType() == Type.STRING)
        {
            return new StringPrimitive("void", context).add(other);
        }

        return super.add(other);
    }
}
