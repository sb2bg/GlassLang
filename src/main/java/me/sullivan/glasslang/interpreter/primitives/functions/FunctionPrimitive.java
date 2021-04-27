package me.sullivan.glasslang.interpreter.primitives.functions;

import me.sullivan.glasslang.interpreter.primitives.Primitive;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.parser.nodes.Node;

import java.util.List;

public class FunctionPrimitive extends FunctionBasePrimitive
{
    public FunctionPrimitive(String name, Node value, List<Token> args, Context context)
    {
        super(name, value, args, context);
    }

    @Override
    public Primitive<?> call(List<Node> argNodes, Context runtime)
    {
        return registerArgs(argNodes, args, getExecution(displayName, runtime)).visitNode(value);
    }
}
