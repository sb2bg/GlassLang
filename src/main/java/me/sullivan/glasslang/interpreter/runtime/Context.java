package me.sullivan.glasslang.interpreter.runtime;

import me.sullivan.glasslang.interpreter.primitives.BooleanPrimitive;
import me.sullivan.glasslang.interpreter.primitives.Primitive;
import me.sullivan.glasslang.interpreter.primitives.VoidPrimitive;
import me.sullivan.glasslang.interpreter.runtime.tables.VariableTable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Context
{

    public enum Keyword
    {
        TRUE("true"), FALSE("false"), VOID("void");

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
        DEFAULTS.put(Keyword.TRUE, new BooleanPrimitive(true, null));
        DEFAULTS.put(Keyword.FALSE, new BooleanPrimitive(false, null));
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

    public Context(Context parent, String context)
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

    public Context getParent()
    {
        return parent;
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
