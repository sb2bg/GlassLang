package me.sullivan.glasslang.parser.nodes;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.types.Primitive;

import java.util.List;

public class ListNode extends Node
{
    private final List<Node> nodes;

    public ListNode(List<Node> nodes)
    {
        super(null);
        this.nodes = nodes;
    }

    @Override
    public List<Node> getValue()
    {
        return nodes;
    }

    @Override
    public String toString()
    {
        return "List" + nodes;
    }

    @Override
    public Primitive<?> visitor(Interpreter interpreter)
    {
        return interpreter.visitListNode(this);
    }
}