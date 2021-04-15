package me.sullivan.glasslang.parser.nodes;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.Primitive;

import java.util.List;

public class CallNode extends Node
{

    private final Node atom;
    private final List<Node> argNodes;

    public CallNode(Node atom, List<Node> argNodes)
    {
        super(null);
        this.atom = atom;
        this.argNodes = argNodes;
    }

    @Override
    public String getValue()
    {
        return atom.getToken().getValue();
    }

    @Override
    public String toString()
    {
        return "CallNode(" + atom.getToken().getValue() + ", " + argNodes + ")";
    }

    @Override
    public Primitive<?> visitor(Interpreter interpreter)
    {
        return interpreter.visitCallNode(this);
    }

    public List<Node> getArgNodes()
    {
        return argNodes;
    }

    public Node nodeToCall()
    {
        return atom;
    }
}