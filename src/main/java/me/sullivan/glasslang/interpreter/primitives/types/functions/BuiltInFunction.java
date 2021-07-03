package me.sullivan.glasslang.interpreter.primitives.types.functions;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.primitives.types.*;
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
        builtIns.put("input", new Pair<>(BuiltInFunction::input, new String[]{"value"})); // TODO allow no args as well
        builtIns.put("is", new Pair<>(BuiltInFunction::is, new String[]{"left", "right"}));
        builtIns.put("length", new Pair<>(BuiltInFunction::length, new String[]{"value"}));
        builtIns.put("randInt", new Pair<>(BuiltInFunction::randInt, new String[]{"value"}));
        builtIns.put("randFloat", new Pair<>(BuiltInFunction::randFloat, new String[]{"value"}));
        builtIns.put("parse", new Pair<>(BuiltInFunction::parse, new String[]{"left", "right"}));
        builtIns.put("in", new Pair<>(BuiltInFunction::in, new String[]{"left", "right"}));
        builtIns.put("typeOf", new Pair<>(BuiltInFunction::typeOf, new String[]{"value"}));
        builtIns.put("systime", new Pair<>(BuiltInFunction::systime, new String[0]));
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

    private static Primitive<?> input(Context context, List<Token> args)
    {
        print(context, args);
        return new StringPrimitive(Shell.SCANNER.nextLine(), context);
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
        else
        {
            return left.equal(right);
        }
    }

    private static Primitive<?> in(Context context, List<Token> args)
    {
        Primitive<?> left = fromTable(0, args, context);
        Primitive<?> right = fromTable(1, args, context);

        return switch (right.getType())
                {
                    case LIST -> new BooleanPrimitive(Primitive.<ListPrimitive>cast(right).getValue().contains(left), context);
                    case STRING -> new BooleanPrimitive(Primitive.<StringPrimitive>cast(right).contains(left.getValue(Type.STRING)), context);
                    default -> new BooleanPrimitive(false, context);
                };
    }

    private static Primitive<?> length(Context context, List<Token> args)
    {
        Primitive<?> other = fromTable(0, args, context);

        return switch (other.getType())
                {
                    case LIST -> new NumberPrimitive(Primitive.<ListPrimitive>cast(other).getValue().size(), context);
                    case STRING -> new NumberPrimitive(Primitive.<StringPrimitive>cast(other).getValue().length(), context);
                    case DICTIONARY -> new NumberPrimitive(Primitive.<DictionaryPrimitive>cast(other).getValue().size(), context);
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

    private static Primitive<?> parse(Context context, List<Token> args)
    {
        Primitive<?> left = fromTable(0, args, context);
        TypePrimitive right = fromTable(1, args, context).getValue(Type.TYPE);

        return left.parse(right);
    }

    private static Primitive<?> typeOf(Context context, List<Token> args)
    {
        return new TypePrimitive(fromTable(0, args, context).getType(), context);
    }

    private static Primitive<?> systime(Context context, List<Token> args)
    {
        return new NumberPrimitive(System.currentTimeMillis(), context);
    }

    @Override
    public Primitive<?> call(List<Node> argNodes, Context runtime)
    {
        Pair<BuiltIn, String[]> method = builtIns.get(name);

        if (method == null)
        {
            throw new RuntimeError("GlassLang has encountered an error. Builtin " + name + " is undefined.", context);
        }

        Context context = registerArgs(argNodes, args, getExecution(displayName, runtime)).context();

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
