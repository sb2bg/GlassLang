package me.sullivan.glasslang.parser.nodes;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.Primitive;
import me.sullivan.glasslang.lexer.token.Token;

public class AssignmentNode extends Node
{
    private Token operator;
    private Node node;

    public AssignmentNode(Token identifier, Token operator, Node node)
    {
        super(identifier);
        this.operator = operator;
        this.node = node;
    }

    @Override
    public Node getValue()
    {
        return node;
    }

    @Override
    public String toString()
    {
        return "Assignment(" + token.getValue() + ", " + getValue() + ")";
    }

    public Token getOperator()
    {
        return operator;
    }

    @Override
    public Primitive<?> visitor(Interpreter interpreter)
    {
        return interpreter.visitAssignmentNode(this);
    }
}