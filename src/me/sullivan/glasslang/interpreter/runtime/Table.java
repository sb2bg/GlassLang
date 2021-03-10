package me.sullivan.glasslang.interpreter.runtime;

import java.text.MessageFormat;
import java.util.HashMap;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;

public class Table<C extends Table<C, V>, V> {

	protected C parentTable;
	protected HashMap<String, V> table = new HashMap<>();
	
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
		if (variable.equals("null"))
		{
			throw new RuntimeError("Cannot assign a value to null");
		}
		
		if (!table.containsKey(variable))
		{
			throw new RuntimeError(MessageFormat.format("Varible ''{0}'' not defined", variable));
		}
		
		return table.get(variable);
	}
	
	public V set(String variable, V value)
	{
		table.put(variable, value);
		return value;
	}
	
	public void delete(String variable)
	{
		table.remove(variable);
	}
}
