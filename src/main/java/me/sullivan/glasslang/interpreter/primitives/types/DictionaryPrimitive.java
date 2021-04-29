package me.sullivan.glasslang.interpreter.primitives.types;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.lexer.token.TokenType;
import me.sullivan.glasslang.parser.nodes.Node;

import java.util.*;

public class DictionaryPrimitive extends Primitive<TreeMap<Node, Node>>
{
    public DictionaryPrimitive(TreeMap<Node, Node> value, Context context)
    {
        super(value, Type.DICTIONARY, context, new HashMap<>());
    }

    @Override
    public Primitive<?> add(Primitive<?> other)
    {
        if (other.getType() == Type.DICTIONARY)
        {
            value.putAll(Primitive.<DictionaryPrimitive>cast(other).getValue());
        }

        return this;
    }

    @Override
    public Primitive<?> min(Primitive<?> other)
    {
        NumberPrimitive otherValue = other.getValue(Type.NUMBER);

        int index = otherValue.getValue().intValue();
        index = index < 0 ? value.size() + index : index;

        value.remove(value.entrySet().stream().toList().get(index).getKey());
        return this;
    }

    @Override
    public Primitive<?> call(List<Node> argNodes, Context runtime)
    {
        List<Token> args = new ArrayList<>()
        {{
            add(new Token(TokenType.IDENTIFIER, "value"));
        }};
        Interpreter interpreter = registerArgs(argNodes, args, getExecution("internal.getMapValue", runtime));

        Node node = value.get(argNodes.get(0));

        return node != null ? interpreter.visitNode(value.get(argNodes.get(0))) : new VoidPrimitive();
    }
}
