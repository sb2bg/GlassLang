package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.parser.nodes.Node;

import java.text.MessageFormat;
import java.util.List;

public class FunctionPrimitive extends Primitive<Node>
{
    private final String name;
    private final List<Token> args;

    public FunctionPrimitive(String name, Node body, List<Token> args, Context context)
    {
        super(body, Type.FUNCTION, context);
        this.name = name;
        this.args = args;
    }

    // FIXME Doesn't work
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
        Interpreter interpreter = new Interpreter(new Context(context, MessageFormat.format("func<{0}>", name)));

        int passed = args.size();
        int expected = argNodes.size();

        if (passed != expected)
        {
            throw new RuntimeError(Math.abs(passed - expected) + " too " + (passed > expected ? "few " : "many ") + "arguments were passed in", context);
        }

        for (int i = 0; i < args.size(); i++)
        {
            Token argName = args.get(i);
            Node argValue = argNodes.get(i);
            interpreter.context().getTable().set(argName.getValue(), interpreter.visitNode(argValue), context);
        }

        return interpreter.visitNode(value);
    }
}
