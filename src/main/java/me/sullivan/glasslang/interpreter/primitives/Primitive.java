package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.primitives.parsing.ParseMethod;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.parser.nodes.Node;

import java.util.*;

public abstract class Primitive<T>
{
    protected Context context;
    protected T value;
    protected Type type;
    protected Map<Type, ParseMethod> parseMethods;

    public Primitive(T value, Type type, Context context, Map<Type, ParseMethod> parseMethods)
    {
        this.value = value;
        this.type = type;
        this.context = context;
        this.parseMethods = parseMethods;

        // TODO fix - println(parse(44, str) + 4) (output is 44.04, should be 444)
        for (Type parseType : Type.values())
        {
            if (parseMethods.containsKey(parseType))
            {
                continue;
            }

            parseMethods.put(parseType, () -> new StringPrimitive(this.toString(), context));
        }
    }

    public T getValue()
    {
        return value;
    }


    public Primitive<?> add(Primitive<?> other)
    {
        throw new RuntimeError("Cannot add " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
    }

    public Primitive<?> min(Primitive<?> other)
    {
        throw new RuntimeError("Cannot subtract " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
    }

    public NumberPrimitive div(Primitive<?> other)
    {
        throw new RuntimeError("Cannot divide " + getClass().getSimpleName() + " with " + other.getClass().getSimpleName(), context);
    }

    public Primitive<?> mul(Primitive<?> other)
    {
        throw new RuntimeError("Cannot multiply " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
    }

    public Primitive<?> mod(Primitive<?> other)
    {
        throw new RuntimeError("Cannot mod " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
    }

    public NumberPrimitive pow(Primitive<?> other)
    {
        throw new RuntimeError("Cannot power " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
    }

    public Primitive<?> less(Primitive<?> other)
    {
        throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
    }

    public BooleanPrimitive lessEqual(Primitive<?> other)
    {
        throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
    }

    public BooleanPrimitive greater(Primitive<?> other)
    {
        throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
    }

    public BooleanPrimitive greaterEqual(Primitive<?> other)
    {
        throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
    }

    public BooleanPrimitive equal(Primitive<?> other)
    {
        return new BooleanPrimitive(this.equals(other), context);
    }

    public BooleanPrimitive notEqual(Primitive<?> other)
    {
        throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
    }

    public BooleanPrimitive and(Primitive<?> other)
    {
        throw new RuntimeError("Cannot use 'and' on " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
    }

    public BooleanPrimitive or(Primitive<?> other)
    {
        throw new RuntimeError("Cannot use 'or' on " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
    }

    public BooleanPrimitive not()
    {
        throw new RuntimeError("Cannot use 'not' on " + getClass().getSimpleName() + "", context);
    }

    public boolean isTrue()
    {
        throw new RuntimeError("Cannot check if " + getClass().getSimpleName() + " is true", context);
    }

    public Primitive<?> call(List<Node> argNodes, Context runtime)
    {
        throw new RuntimeError("Cannot call " + getClass().getSimpleName(), context);
    }

    public Context getContext()
    {
        return context;
    }

    @SuppressWarnings("unchecked")
    public <CT extends Primitive<?>> CT getValue(Type type)
    {
        if (type != this.getType())
        {
            throw new RuntimeError("Expected type " + type, context);
        }

        return (CT) this;
    }

    public String toString()
    {
        return value != null ? value.toString() : "void";
    }

    public Type getType()
    {
        return type;
    }

    public void validate(TypePrimitive type)
    {
        if (parseMethods.containsKey(type.getValue()))
        {
            return;
        }

        throw new RuntimeError("Cannot cast " + this + " of type " + getType() + " to type " + type, context);
    }

    public Primitive<?> parse(TypePrimitive type)
    {
        validate(type);
        return parseMethods.get(type.getValue()).parse();
    }

    @SuppressWarnings("unchecked")
    public static <CT extends Primitive<?>> CT cast(Primitive<?> value)
    {
        return (CT) value;
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o || o instanceof Primitive<?> primitive
                && primitive.type == type && primitive.getValue().equals(value);
    }


    protected void validateArgs(List<Node> passed, List<Token> args)
    {
        int argLen = args.size();
        int passedLen = passed.size();

        if (argLen != passedLen)
        {
            throw new RuntimeError(Math.abs(passedLen - argLen) + " too " + (passedLen > argLen ? "many " : "few ") + "arguments were passed in", context);
        }
    }

    protected Interpreter getExecution(String name, Context context)
    {
        return new Interpreter(context == null ? new Context(name, Context.getGlobalContext().getTable()) : new Context(context, name));
    }

    protected Interpreter registerArgs(List<Node> passed, List<Token> args, Interpreter execution)
    {
        validateArgs(passed, args);

        for (int i = 0; i < passed.size(); i++)
        {
            Token argName = args.get(i);
            Node argValue = passed.get(i);
            execution.context().getTable().set(argName.getValue(), execution.visitNode(argValue), context);
        }

        return execution;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(value, type);
    }
}
