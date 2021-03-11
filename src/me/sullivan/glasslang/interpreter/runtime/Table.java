package me.sullivan.glasslang.interpreter.runtime;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Set;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;

public class Table<C extends Table<C, V>, V> {

	protected C parentTable;
	protected HashMap<String, V> table = new HashMap<>();
	private static final Set<String> PROTECTED = Context.GlobalContext.getDefaults().keySet();

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

	public V get(String variable)
	{		
		if (!table.containsKey(variable))
		{
			throw new RuntimeError(MessageFormat.format("Varible ''{0}'' not defined", variable));
		}

		return table.get(variable);
	}

	public V set(String variable, V value, boolean internal)
	{
		if (!internal)
		{
			validate(variable);
		}

		table.put(variable, value);
		return value;
	}

	public void delete(String variable)
	{
		table.remove(variable);
	}

	private void validate(String toVal)
	{
		if (PROTECTED.contains(toVal))
		{
			throw new RuntimeError("Cannot assign a value to " + toVal);
		}
	}
}
