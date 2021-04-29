package me.sullivan.glasslang.interpreter.primitives.types;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.parser.nodes.Node;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;

public class ListPrimitive extends Primitive<List<Primitive<?>>>
{
    public ListPrimitive(List<Primitive<?>> value, Context context)
    {
        super(value, Type.LIST, context, new HashMap<>());
    }

    @Override
    public Primitive<?> add(Primitive<?> other)
    {
        if (other.getType() == Type.LIST)
        {
            value.addAll(Primitive.<ListPrimitive>cast(other).getValue());
        }
        else
        {
            value.add(other);
        }

        return this;
    }

    @Override
    public Primitive<?> min(Primitive<?> other)
    {
        NumberPrimitive otherValue = other.getValue(Type.NUMBER);

        int index = otherValue.getValue().intValue();
        index = index < 0 ? value.size() + index : index;

        value.remove(index);
        return this;
    }

    @Override
    public Primitive<?> call(List<Node> argNodes, Context runtime)
    {
        return value.get(index(argNodes, runtime, value.size()));
    }
}
