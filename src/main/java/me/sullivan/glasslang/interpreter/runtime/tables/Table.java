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

    public V get(String variable, Context context)
    {
        if (table.containsKey(variable))
        {
            return table.get(variable);
        }
        else if (parentTable != null)
        {
            return parentTable.get(variable, context);
        }

        throw new RuntimeError(MessageFormat.format("{0} is undefined", variable), context);
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
