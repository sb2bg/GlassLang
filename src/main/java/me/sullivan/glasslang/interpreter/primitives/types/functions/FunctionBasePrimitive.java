package me.sullivan.glasslang.interpreter.primitives.types.functions;

import me.sullivan.glasslang.interpreter.primitives.types.Primitive;
import me.sullivan.glasslang.interpreter.primitives.types.Type;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.parser.nodes.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class FunctionBasePrimitive extends Primitive<Node>
{
    protected final String name;
    protected final String displayName;
    protected final List<Token> args;

    public FunctionBasePrimitive(String name, Node value, List<Token> args, Context context)
    {
        super(value, Type.FUNCTION, context, new HashMap<>());
        this.name = name == null ? "anon-" + UUID.randomUUID() : name;
        this.displayName = "func<" + this.name + ">";
        this.args = args;
    }

    @Override
    public String toString()
    {
        return displayName;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }
        FunctionBasePrimitive that = (FunctionBasePrimitive) o;
        return Objects.equals(name, that.name) && Objects.equals(displayName, that.displayName) && Objects.equals(args, that.args);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), name, displayName, args);
    }
}
