package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.parser.nodes.Node;

import java.util.HashMap;
import java.util.List;

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
            return other;
        }

        return super.add(other);
    }
}
