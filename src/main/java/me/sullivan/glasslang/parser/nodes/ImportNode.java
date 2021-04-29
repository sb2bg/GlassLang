package me.sullivan.glasslang.parser.nodes;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.types.Primitive;
import me.sullivan.glasslang.lexer.token.Token;

public class ImportNode extends Node
{
    public ImportNode(Token token)
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
        return "ImportNode(" + getValue() + ")";
    }

    @Override
    public Primitive<?> visitor(Interpreter interpreter)
    {
        return interpreter.visitImportNode(this);
    }
}