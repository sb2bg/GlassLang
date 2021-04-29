package me.sullivan.glasslang.parser.nodes;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.types.Primitive;
import me.sullivan.glasslang.lexer.token.Token;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class Node implements Comparable<Node>
{
    protected Token token;

    public Node(Token token)
    {
        this.token = token;
    }

    public Token getToken()
    {
        return token;
    }

    public abstract Object getValue();

    public abstract String toString();

    public abstract Primitive<?> visitor(Interpreter interpreter);

    @Override
    public int compareTo(@NotNull Node o)
    {
        return equals(o) ? 0 : 1;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Node node))
        {
            return false;
        }
        return Objects.equals(token, node.token);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(token);
    }
}