package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.parser.nodes.Node;

import java.text.MessageFormat;
import java.util.List;

public class ListPrimitive extends Primitive<List<Primitive<?>>>
{

    public ListPrimitive(List<Primitive<?>> value, Context context)
    {
        super(value, Type.LIST, context);
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

    // TODO there has to be a better way than this? aka improve it smh
    @Override
    public Primitive<?> call(List<Node> argNodes)
    {
        if (argNodes.size() != 1)
        {
            throw new RuntimeError("Expected one argument", context);
        }

        Interpreter interpreter = new Interpreter(new Context(context, MessageFormat.format("func<{0}>", "get-list.index")));
        NumberPrimitive otherValue = interpreter.visitNode(argNodes.get(0)).getValue(Type.NUMBER);
        int index = otherValue.getValue().intValue();

        if (index >= value.size() || index < 0)
        {
            throw new RuntimeError(MessageFormat.format("Index {0} out of bounds for range {1}", index, value.size()), context);
        }

        return value.get(index);
    }
}
