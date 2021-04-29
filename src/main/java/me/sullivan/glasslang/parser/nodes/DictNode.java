package me.sullivan.glasslang.parser.nodes;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.types.Primitive;

import java.util.Map;
import java.util.TreeMap;

public class DictNode extends Node
{
    private TreeMap<Node, Node> map;

    public DictNode(TreeMap<Node, Node> map)
    {
        super(null);
        this.map = map;
    }

    @Override
    public TreeMap<Node, Node> getValue()
    {
        return map;
    }

    @Override
    public String toString()
    {
        return "Map" + map;
    }

    @Override
    public Primitive<?> visitor(Interpreter interpreter)
    {
        return interpreter.visitMapNode(this);
    }
}
