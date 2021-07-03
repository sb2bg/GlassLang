package me.sullivan.glasslang.interpreter.primitives.types;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.lexer.token.TokenType;
import me.sullivan.glasslang.parser.nodes.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DictionaryPrimitive extends Primitive<LinkedHashMap<Primitive<?>, Primitive<?>>>
{
    public DictionaryPrimitive(LinkedHashMap<Primitive<?>, Primitive<?>> value, Context context)
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

    // TODO when using (in at LEAST in this case) a list and index as key, (was string in specific case), it only kept one key
    /*
    names = ["night", "astro"]
    test = { names(0): false, names(1): true }
    println("LIST: " + parse(names, str) + " DICT: " + parse(test, str))
     */
    @Override
    public Primitive<?> call(List<Node> argNodes, Context runtime)
    {
        List<Token> args = new ArrayList<>()
        {{
            add(new Token(TokenType.IDENTIFIER, "value"));
        }};

        Interpreter interpreter = registerArgs(argNodes, args, getExecution("internal.getMapValue", runtime));
        Primitive<?> v = value.get(interpreter.visitNode(argNodes.get(0)));

        return v == null ? new VoidPrimitive() : v;
    }
}
