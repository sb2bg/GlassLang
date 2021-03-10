package me.sullivan.glasslang.interpreter.runtime;

import me.sullivan.glasslang.interpreter.primitves.NumberPrimitive;

public class Context {

	private Context parent;
	private String context;
	private VariableTable varTable;
	
	public Context(Context parent, String context, VariableTable varTable)
	{
		this.parent = parent;
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
	
	public static class GlobalContext
	{
		public static Context createGlobalContext()
		{
			VariableTable table = new VariableTable();
			table.set("null", new NumberPrimitive(0));
			
			return new Context(null, "<glsmain>", table);
		}
	}
}
