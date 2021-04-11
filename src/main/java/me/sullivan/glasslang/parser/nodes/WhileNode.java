package me.sullivan.glasslang.parser.nodes;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.Primitive;

public class WhileNode extends Node {

    private Node condition;
    private Node eval;

    public WhileNode(Node condition, Node eval)
    {
        super(null);
        this.condition = condition;
        this.eval = eval;
    }

    @Override
    public Node getValue()
    {
        return eval;
    }

    @Override
    public String toString()
    {
        return "While(" + condition + ") => " + eval;
    }

    @Override
    public Primitive<?> visitor(Interpreter interpreter)
    {
        return interpreter.visitWhileNode(this);
    }

    public Node getCondition()
    {
        return condition;
    }
}