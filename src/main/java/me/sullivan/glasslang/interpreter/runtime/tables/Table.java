package me.sullivan.glasslang.interpreter.runtime.tables;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Set;

public class Table<C extends Table<C, V>, V>
{

    protected C parentTable;
    protected HashMap<String, V> table = new HashMap<>();
    private static final Set<String> PROTECTED = Context.getDefaults();

    public Table()
    {
    }

    public Table(C parentTable)
    {
        this.parentTable = parentTable;
    }

    public C getParentTable()
    {
        return parentTable;
    }

    public V get(String variable, Context context)
    {
        Table<C, V> search = this;

        while (!search.contains(variable))
        {
            if (search.getParentTable() == null)
            {
                throw new RuntimeError(MessageFormat.format("{0} is undefined", variable), context);
            }

            if (search.getParentTable().contains(variable))
            {
                return search.getParentTable().get(variable, context);
            }

            search = search.getParentTable();
        }

        return table.get(variable);
    }

    public V set(String variable, V value, Context context)
    {
        validate(variable, context);

        return inject(variable, value);
    }

    public V inject(String variable, V value)
    {
        table.put(variable, value);
        return value;
    }

    public void delete(String variable)
    {
        table.remove(variable);
    }

    public boolean contains(String variable)
    {
        return table.containsKey(variable);
    }

    private void validate(String toVal, Context context)
    {
        if (PROTECTED.contains(toVal))
        {
            throw new RuntimeError("Cannot assign a value to '" + toVal + "'", context);
        }
    }
}
