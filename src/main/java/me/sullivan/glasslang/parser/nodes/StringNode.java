package me.sullivan.glasslang.parser.nodes;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.Primitive;
import me.sullivan.glasslang.lexer.token.Token;

public class StringNode extends Node {

    public StringNode(Token token)
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
        return "String(" + token.getValue() + ")";
    }

    @Override
    public Primitive<?> visitor(Interpreter interpreter)
    {
        return interpreter.visitStringNode(this);
    }
}