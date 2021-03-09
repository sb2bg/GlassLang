package me.sullivan.glasslang.interpreter.runtime;

import java.util.HashMap;

public class Table<C extends Table<C, V>, V> {

	private C parentTable;
	private HashMap<String, V> table = new HashMap<>();
	
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
		return table.get(variable);
	}
	
	public void set(String variable, V value)
	{
		table.put(variable, value);
	}
	
	public void delete(String variable)
	{
		table.remove(variable);
	}
}
