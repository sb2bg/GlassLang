package me.sullivan.glasslang.interpreter.runtime;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.primitives.types.functions.BuiltInFunction;
import me.sullivan.glasslang.interpreter.primitives.types.*;
import me.sullivan.glasslang.interpreter.runtime.tables.VariableTable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Context
{
    public enum Keyword
    {
        TRUE("true"), FALSE("false"), STRING("string"), NUMBER("number"), LIST("list"), FUNCTION("function"), TYPE("type"),
        BOOLEAN("bool"), VOID("void");

        private final String keyword;

        Keyword(String keyword)
        {
            this.keyword = keyword;
        }

        public String getKeyword()
        {
            return keyword;
        }
    }

    private static final HashMap<String, Primitive<?>> DEFAULTS = new HashMap<>();

    static
    {
        for (String name : BuiltInFunction.getBuiltInNames())
        {
            DEFAULTS.put(name, new BuiltInFunction(name));
        }

        for (Type type : Type.values())
        {
            if (type.getName() == null)
            {
                continue;
            }
            DEFAULTS.put(type.getName(), new TypePrimitive(type, null));
        }

        DEFAULTS.put(Keyword.TRUE.getKeyword(), new BooleanPrimitive(true, null));
        DEFAULTS.put(Keyword.FALSE.getKeyword(), new BooleanPrimitive(false, null));
        DEFAULTS.put(Keyword.VOID.getKeyword(), new VoidPrimitive());
    }

    private static final Context GLOBAL = new Context("<gls-main>", createGlobalTable());

    private static VariableTable createGlobalTable()
    {
        VariableTable table = new VariableTable();

        for (String keyword : DEFAULTS.keySet())
        {
            table.inject(keyword, DEFAULTS.get(keyword));
        }

        return table;
    }

    public static Context getGlobalContext()
    {
        return GLOBAL;
    }

    public static Set<String> getDefaults()
    {
        return new HashSet<>(DEFAULTS.keySet());
    }

    protected int depth;
    protected Context parent;
    protected String context;
    protected VariableTable varTable;

    // TODO Possibly fixed (stackoverflow), maybe a more elegant solution next?
    public Context(Context parent, String context)
    {
        this.parent = parent;
        this.context = context;
        this.varTable = new VariableTable(parent.getTable());
        this.depth = parent.depth + 1;

        if (depth > 500)
        {
            throw new RuntimeError("StackOverFlowException, make sure you don't have an infinite cycle of functions. Depth=" + depth, this);
        }
    }

    public Context(String context, VariableTable varTable)
    {
        this.parent = null;
        this.context = context;
        this.varTable = varTable;
    }

    public String getContext()
    {
        return context;
    }

    public VariableTable getTable()
    {
        return varTable;
    }

    @Override
    public String toString()
    {
        return "Context{" +
                "parent=" + parent +
                ", context='" + context + '\'' +
                ", varTable=" + varTable +
                '}';
    }
}
