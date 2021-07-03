package me.sullivan.glasslang.parser.nodes.ifnodes;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.types.Primitive;
import me.sullivan.glasslang.parser.nodes.Node;

public class IfNode extends Node
{
    protected final Node condition;
    protected final Node execute;
    protected final Node elseCase;
    protected boolean statement;

    public IfNode(Node condition, Node execute, Node elseCase, boolean statement)
    {
        super(null);
        this.condition = condition;
        this.execute = execute;
        this.elseCase = elseCase;
        this.statement = statement;
    }

    @Override
    public Node getValue()
    {
        return execute;
    }

    @Override
    public String toString()
    {
        return "If(" + condition + " => " + execute + ")" + " " + elseCase;
    }

    public Node getElseCase()
    {
        return elseCase;
    }

    public boolean isStatement()
    {
        return statement;
    }

    public Node getCondition()
    {
        return condition;
    }

    @Override
    public Primitive<?> visitor(Interpreter interpreter)
    {
        return interpreter.visitIfNode(this);
    }
}
