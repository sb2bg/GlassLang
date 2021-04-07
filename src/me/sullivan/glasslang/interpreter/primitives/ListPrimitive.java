package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.parser.nodes.Node;

import java.util.List;

public class ListPrimitive extends Primitive<List<Node>> {

    public ListPrimitive(List<Node> value, Context context)
    {
        super(value, Type.LIST, context);
    }
}
