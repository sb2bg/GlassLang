package me.sullivan.glasslang.parser.nodes;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.Primitive;
import me.sullivan.glasslang.lexer.token.Token;

public class VariableNode extends Node
{

    public VariableNode(Token token)
    {
        super(token);
    }

    @Override
    public String getValue()
    {
        return token.getValue();
    }

    @Override
    public String toString()
    {
        return "Variable(" + getValue() + ")";
    }

    @Override
    public Primitive<?> visitor(Interpreter interpreter)
    {
        return interpreter.visitVariableNode(this);
    }
}
