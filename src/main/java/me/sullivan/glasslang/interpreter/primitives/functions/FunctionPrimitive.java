package me.sullivan.glasslang.interpreter.primitives.functions;

import me.sullivan.glasslang.interpreter.primitives.Primitive;
import me.sullivan.glasslang.interpreter.primitives.functions.FunctionBasePrimitive;
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

    // FIXME Doesn't work :(
    // {
    //	 @def(otherFunc, a) => otherFunc(a)
    //
    //	 @test(a) => a
    //
    //	 def(test, 1)
    // }
    @Override
    public Primitive<?> call(List<Node> argNodes)
    {
        return registerArgs(argNodes, args, getExecution(displayName)).visitNode(value);
    }
}
