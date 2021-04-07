package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.interpreter.runtime.tables.VariableTable;
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
        if (other.type == Type.LIST)
        {
            ListPrimitive value = other.getValue(Type.LIST);
            this.value.addAll(value.getValue());
        }
        else
        {
            value.add(other);
        }

        return new ListPrimitive(this.value, context);
    }

    @Override
    public Primitive<?> min(Primitive<?> other)
    {
        if (other.type != Type.NUMBER)
        {
            throw new RuntimeError("Expected number to be passed, not" + other.getType());
        }

        NumberPrimitive value = other.getValue(Type.NUMBER);
        int num = value.getValue().intValue();

        if (num < 0)
        {
            num = this.value.size() + num;
        }

        this.value.remove(num);

        return new ListPrimitive(this.value, context);
    }

    @Override
    public Primitive<?> call(List<Node> argNodes)
    {
        if (argNodes.size() != 1)
        {
            throw new RuntimeError("Expected one argument");
        }

        Interpreter interpreter = new Interpreter(new Context(context, MessageFormat.format("func<{0}>", "ind-get"), new VariableTable(context.getTable())));
        Primitive<?> index = interpreter.visitNode(argNodes.get(0));

        if (index.getType() != Type.NUMBER)
        {
            throw new RuntimeError("Expected number, not " + index.getType());
        }

        NumberPrimitive indexNum = index.getValue(Type.NUMBER);
        int value = indexNum.getValue().intValue();

        if (value >= this.value.size() || value < 0)
        {
            throw new RuntimeError(MessageFormat.format("Index {0} out of bounds for range {1}", value, this.value.size()));
        }

        return this.value.get(indexNum.getValue().intValue());
    }
}
