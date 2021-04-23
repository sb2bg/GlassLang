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

    // TODO there has to be a better way than this? aka improve it smh
    @Override
    public Primitive<?> call(List<Node> argNodes)
    {
        if (argNodes.size() > 1)
        {
            throw new RuntimeError("Expected one argument in primitive constructor", context);
        }

        return null;
    }
}
