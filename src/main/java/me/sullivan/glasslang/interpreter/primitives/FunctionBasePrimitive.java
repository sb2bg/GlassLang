package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.parser.nodes.Node;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

public class FunctionBasePrimitive extends Primitive<Node>
{
    protected final String name;
    protected final List<Token> args;

    public FunctionBasePrimitive(String name, Node value, List<Token> args, Context context)
    {
        super(value, Type.FUNCTION, context);
        this.name = name == null ? "anon-" + UUID.randomUUID() : name;
        this.args = args;
    }

    protected void validateArgs(List<Node> passed)
    {
        int argLen = args.size();
        int passedLen = passed.size();

        if (argLen != passedLen)
        {
            throw new RuntimeError(Math.abs(passedLen - argLen) + " too " + (passedLen > argLen ? "few " : "many ") + "arguments were passed in", context);
        }
    }

    protected Interpreter getExecution()
    {
        String contextName = MessageFormat.format("func<{0}>", name);
        return new Interpreter(context == null ? new Context(contextName, Context.getGlobalContext().getTable()) : new Context(context, contextName));
    }

    protected Interpreter registerArgs(List<Node> passed, Interpreter execution)
    {
        validateArgs(passed);

        for (int i = 0; i < passed.size(); i++)
        {
            Token argName = args.get(i);
            Node argValue = passed.get(i);
            execution.context().getTable().set(argName.getValue(), execution.visitNode(argValue), context);
        }

        return execution;
    }
}
