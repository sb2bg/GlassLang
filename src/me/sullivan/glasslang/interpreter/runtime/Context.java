package me.sullivan.glasslang.interpreter.runtime;

import java.util.HashMap;

import me.sullivan.glasslang.interpreter.primitves.NumberPrimitive;

public class Context {

	protected Context parent;
	protected String context;
	protected VariableTable varTable;
	
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
	
	public static class GlobalContext extends Context
	{
		private static final HashMap<String, NumberPrimitive> DEFAULTS = new HashMap<>();
		static
		{
			DEFAULTS.put("null", new NumberPrimitive(0));
			DEFAULTS.put("true", new NumberPrimitive(1));
			DEFAULTS.put("false", new NumberPrimitive(0));
		}
		
		public GlobalContext()
		{
			super(null, "<glsmain>", new VariableTable());
			
			for (String key : DEFAULTS.keySet())
			{
				getTable().set(key, DEFAULTS.get(key), true);
			}
		}
		
		public static HashMap<String, NumberPrimitive> getDefaults()
		{
			return DEFAULTS;
		}
	}
}
