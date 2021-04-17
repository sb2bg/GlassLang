package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.parser.nodes.Node;

import java.text.MessageFormat;
import java.util.List;

public class StringPrimitive extends Primitive<String>
{

    public StringPrimitive(String value, Context context)
    {
        super(value, Type.STRING, context);
    }

    @Override
    public Primitive<?> add(Primitive<?> other)
    {
        return switch (other.getType())
                {
                    case STRING, BOOLEAN -> new StringPrimitive(value + other, context);
                    case NUMBER -> new StringPrimitive(value + NumberPrimitive.doubleToString(Primitive.<NumberPrimitive>cast(other).getValue()), context);
                    case VOID -> this;
                    default -> throw new RuntimeError("Cannot concatenate " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
                };
    }

    @Override
    public Primitive<?> mul(Primitive<?> other)
    {
        NumberPrimitive otherValue = other.getValue(Type.NUMBER);
        return new StringPrimitive(value.repeat(otherValue.getValue().intValue()), context);
    }

    // TODO there has to be a better way than this? aka improve it smh
    @Override
    public Primitive<?> call(List<Node> argNodes)
    {
        if (argNodes.size() > 2 || 1 > argNodes.size())
        {
            throw new RuntimeError("Expected one argument", context);
        }

        Interpreter interpreter = new Interpreter(new Context(context, MessageFormat.format("func<{0}>", "get-string.index")));
        Primitive<?> start = interpreter.visitNode(argNodes.get(0));
        Primitive<?> end;

        if (start.getType() != Type.NUMBER)
        {
            throw new RuntimeError("Expected number, not " + start.getType(), context);
        }

        NumberPrimitive indexStart = start.getValue(Type.NUMBER);
        int valueStart = indexStart.getValue().intValue();
        end = new NumberPrimitive(valueStart + 1, context);

        if (argNodes.size() > 1)
        {
            end = interpreter.visitNode(argNodes.get(1));

            if (end.getType() != Type.NUMBER)
            {
                throw new RuntimeError("Expected number, not " + end.getType(), context);
            }
        }

        NumberPrimitive indexEnd = end.getValue(Type.NUMBER);
        int valueEnd = indexEnd.getValue().intValue();

        if (valueStart >= this.value.length() || valueStart < 0 || valueEnd > this.value.length() || valueEnd < valueStart)
        {
            throw new RuntimeError(MessageFormat.format(valueStart > valueEnd ? "Start index {0} must be less than end index {1}" : "Start index {0} or end index {1} out of bounds for range {2}.", valueStart, valueEnd, this.value.length()), context);
        }

        return new StringPrimitive(this.value.substring(valueStart, valueEnd), context);
    }
}
