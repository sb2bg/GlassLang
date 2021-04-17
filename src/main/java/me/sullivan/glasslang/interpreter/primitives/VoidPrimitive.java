package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.parser.nodes.Node;

import java.util.List;

public class VoidPrimitive extends Primitive<Void>
{
    public VoidPrimitive()
    {
        super(null, Type.VOID, null);
    }

    @Override
    public Primitive<?> add(Primitive<?> other)
    {
        return super.add(other);
    }
}
