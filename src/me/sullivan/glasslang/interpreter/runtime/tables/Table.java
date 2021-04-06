package me.sullivan.glasslang.interpreter.runtime.tables;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Set;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;

public class Table<C extends Table<C, V>, V> {

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

	public V get(String variable)
	{		
		if (!table.containsKey(variable))
		{
			throw new RuntimeError(MessageFormat.format("{0} is undefined", variable));
		}

		return table.get(variable);
	}

	public V set(String variable, V value)
	{
		validate(variable);
		
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
		return table.keySet().contains(variable);
	}
	
	private void validate(String toVal)
	{
		if (PROTECTED.contains(toVal))
		{
			throw new RuntimeError("Cannot assign a value to '" + toVal + "'");
		}
	}
}
