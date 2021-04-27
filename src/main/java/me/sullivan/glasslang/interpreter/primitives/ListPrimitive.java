package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.lexer.token.TokenType;
import me.sullivan.glasslang.parser.nodes.Node;

import java.text.MessageFormat;
import java.util.ArrayList;
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

    // TODO fix negative bounds
    @Override
    public Primitive<?> call(List<Node> argNodes, Context runtime)
    {
        ArrayList<Token> args = new ArrayList<>()
        {{
            add(new Token(TokenType.IDENTIFIER, "index"));
        }};

        Context context = registerArgs(argNodes, args, getExecution("internal.list-index", runtime)).context();
        NumberPrimitive indexPrim = context.getTable().get(args.get(0).getValue(), context).getValue(Type.NUMBER);
        int index = indexPrim.getValue().intValue();

        if (index >= value.size() || index < 0)
        {
            throw new RuntimeError(MessageFormat.format("Index {0} out of bounds for range {1}", index, value.size()), context);
        }

        return value.get(index);
    }
}
