package me.sullivan.glasslang.interpreter.runtime;

import me.sullivan.glasslang.interpreter.primitives.*;
import me.sullivan.glasslang.interpreter.runtime.tables.VariableTable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Context
{
    public enum Keyword
    {
        TRUE("true"), FALSE("false"), STRING("str"), NUMBER("num"), LIST("list"), FUNCTION("func"), TYPE("type"),
        BOOLEAN("bool"), IS("is"), PRINT("print"), PRINTLN("println"), STR_INPUT("strInput"), NUM_INPUT("numInput"),
        VOID("void");

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

    private static final HashMap<Keyword, Primitive<?>> DEFAULTS = new HashMap<>();

    static
    {
        DEFAULTS.put(Keyword.IS, new BuiltInFunction(Keyword.IS.getKeyword()));
        DEFAULTS.put(Keyword.PRINT, new BuiltInFunction(Keyword.PRINT.getKeyword()));
        DEFAULTS.put(Keyword.PRINTLN, new BuiltInFunction(Keyword.PRINTLN.getKeyword()));
        DEFAULTS.put(Keyword.STR_INPUT, new BuiltInFunction(Keyword.STR_INPUT.getKeyword()));
        DEFAULTS.put(Keyword.NUM_INPUT, new BuiltInFunction(Keyword.NUM_INPUT.getKeyword()));

        DEFAULTS.put(Keyword.FALSE, new BooleanPrimitive(false, null));
        DEFAULTS.put(Keyword.STRING, new TypePrimitive(Type.STRING, null));
        DEFAULTS.put(Keyword.NUMBER, new TypePrimitive(Type.NUMBER, null));
        DEFAULTS.put(Keyword.LIST, new TypePrimitive(Type.LIST, null));
        DEFAULTS.put(Keyword.FUNCTION, new TypePrimitive(Type.FUNCTION, null));
        DEFAULTS.put(Keyword.TYPE, new TypePrimitive(Type.TYPE, null));
        DEFAULTS.put(Keyword.BOOLEAN, new TypePrimitive(Type.BOOLEAN, null));

        DEFAULTS.put(Keyword.TRUE, new BooleanPrimitive(true, null));

        DEFAULTS.put(Keyword.VOID, new VoidPrimitive());
    }

    private static final Context GLOBAL = new Context("<gls-main>", createGlobalTable());

    private static VariableTable createGlobalTable()
    {
        VariableTable table = new VariableTable();

        for (Keyword keyword : DEFAULTS.keySet())
        {
            table.inject(keyword.getKeyword(), DEFAULTS.get(keyword));
        }

        return table;
    }

    public static Context getGlobalContext()
    {
        return GLOBAL;
    }

    public static Set<String> getDefaults()
    {
        Set<String> defaults = new HashSet<>();

        DEFAULTS.keySet().forEach(key -> defaults.add(key.getKeyword()));

        return defaults;
    }

    protected Context parent;
    protected String context;
    protected VariableTable varTable;

    public Context(@NotNull Context parent, String context)
    {
        this.parent = parent;
        this.context = context;
        this.varTable = new VariableTable(parent.getTable());
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
