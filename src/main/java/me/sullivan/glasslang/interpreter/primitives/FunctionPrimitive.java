package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.parser.nodes.Node;

import java.text.MessageFormat;
import java.util.List;

public class FunctionPrimitive extends FunctionBasePrimitive
{
    public FunctionPrimitive(String name, Node value, List<Token> args, Context context)
    {
        super(name, value, args, context);
    }

    // FIXME Doesn't work :(
    // {
    //	 @func(otherFunc, a) => otherFunc(a)
    //
    //	 @test(a) => a
    //
    //	 func(test, 1)
    // }
    @Override
    public Primitive<?> call(List<Node> argNodes)
    {
        return registerArgs(argNodes, getExecution()).visitNode(value);
    }
}
