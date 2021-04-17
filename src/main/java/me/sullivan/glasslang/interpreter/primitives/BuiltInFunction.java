package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.lexer.token.TokenType;
import me.sullivan.glasslang.parser.nodes.Node;
import me.sullivan.glasslang.shell.Shell;
import org.javatuples.Pair;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public class BuiltInFunction extends FunctionBasePrimitive
{
    public BuiltInFunction(String name)
    {
        super(validateName(name), null, strArrToTokList(builtIns.get(name).getValue1()), null);
    }

    private interface BuiltIn
    {
        Primitive<?> call(Context context, List<Token> args);
    }

    private static final Map<String, Pair<BuiltIn, String[]>> builtIns = new HashMap<>();

    static
    {
        builtIns.put("print", new Pair<>(BuiltInFunction::print, new String[]{"value"}));
        builtIns.put("println", new Pair<>(BuiltInFunction::println, new String[]{"value"}));
        builtIns.put("strInput", new Pair<>(BuiltInFunction::strInput, new String[]{}));
        builtIns.put("numInput", new Pair<>(BuiltInFunction::numInput, new String[]{}));
        builtIns.put("is", new Pair<>(BuiltInFunction::is, new String[]{"left", "right"}));
        builtIns.put("length", new Pair<>(BuiltInFunction::length, new String[]{"value"}));
        builtIns.put("randInt", new Pair<>(BuiltInFunction::randInt, new String[]{"value"}));
        builtIns.put("randFloat", new Pair<>(BuiltInFunction::randFloat, new String[]{"value"}));
        // TODO add math functions, and others such as "in"
    }

    public static Set<String> getBuiltInNames()
    {
        return builtIns.keySet();
    }

    private static Primitive<?> print(Context context, List<Token> args)
    {
        System.out.print(fromTable(0, args, context));
        return new VoidPrimitive();
    }

    private static Primitive<?> println(Context context, List<Token> args)
    {
        System.out.println(fromTable(0, args, context));
        return new VoidPrimitive();
    }

    private static Primitive<?> strInput(Context context, List<Token> args)
    {
        return new StringPrimitive(Shell.SCANNER.nextLine(), context);
    }

    private static Primitive<?> numInput(Context context, List<Token> args)
    {
        try
        {
            return new NumberPrimitive(Shell.SCANNER.nextDouble(), context);
        }
        catch (InputMismatchException e)
        {
            throw new RuntimeError("Result of 'numInput' must be a number.", context);
        }
    }

    private static Primitive<?> is(Context context, List<Token> args)
    {
        Primitive<?> left = fromTable(0, args, context);
        Primitive<?> right = fromTable(1, args, context);

        if (right.getType() == Type.TYPE)
        {
            TypePrimitive other = right.getValue(Type.TYPE);
            return other.is(left);
        }
        else if (right.getType() == Type.LIST)
        {
            List<Primitive<?>> other = right.getValue(Type.LIST);

            if (other.contains(left))
            {
                return new BooleanPrimitive(true, context);
            }

            return new BooleanPrimitive(false, context);
        }
        else
        {
            throw new RuntimeError("Right hand parameter of 'is' must be type or list of types", context);
        }
    }

    private static Primitive<?> length(Context context, List<Token> args)
    {
        Primitive<?> other = fromTable(0, args, context);

        return switch (other.getType())
                {
                    case LIST -> new NumberPrimitive(Primitive.<ListPrimitive>cast(other).getValue().size(), context);
                    case STRING -> new NumberPrimitive(Primitive.<StringPrimitive>cast(other).getValue().length(), context);
                    default -> throw new RuntimeError("Cannot use 'length' on " + other.getType(), context);
                };
    }

    private static Primitive<?> randInt(Context context, List<Token> args)
    {
        NumberPrimitive range = fromTable(0, args, context).getValue(Type.NUMBER);
        return new NumberPrimitive(new SplittableRandom().nextInt(range.getValue().intValue()), context);
    }

    public static Primitive<?> randFloat(Context context, List<Token> args)
    {
        NumberPrimitive range = fromTable(0, args, context).getValue(Type.NUMBER);
        return new NumberPrimitive(new SplittableRandom().nextDouble(range.getValue()), context);
    }

    @Override
    public Primitive<?> call(List<Node> argNodes)
    {
        Pair<BuiltIn, String[]> method = builtIns.get(name);
        Context context = registerArgs(argNodes, getExecution()).context();

        return method.getValue0().call(context, args);
    }

    private static List<Token> strArrToTokList(String[] arr)
    {
        return Arrays.stream(arr).map(token -> new Token(TokenType.IDENTIFIER, token)).collect(Collectors.toList());
    }

    private static Primitive<?> fromTable(int index, List<Token> args, Context context)
    {
        return context.getTable().get(args.get(index).getValue(), context);
    }

    private static String validateName(String name)
    {
        if (builtIns.containsKey(name))
        {
            return name;
        }

        throw new RuntimeError(MessageFormat.format("Unknown builtin ''{0}''", name), null);
    }
}
