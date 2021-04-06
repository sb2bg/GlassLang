package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;

public class StringPrimitive extends Primitive<String> {

	public StringPrimitive(String value, Context context)
	{
		super(value, Type.STRING, context);
	}

	@Override
	public Primitive<?> add(Primitive<?> other)
	{
		if (other.type == Type.STRING)
		{
			StringPrimitive value = other.getValue(Type.STRING);
			return new StringPrimitive(this.value + value.getValue(), context);
		}
		else if (other.type == Type.NUMBER)
		{
			NumberPrimitive value = other.getValue(Type.NUMBER);
			return new StringPrimitive(this.value + NumberPrimitive.doubleToString(value.getValue()), context);
		}
		else if (other.type == Type.BOOLEAN)
		{
			BooleanPrimitive value = other.getValue(Type.BOOLEAN);
			return new StringPrimitive(this.value + value.getValue(), context);
		}
		
		throw new RuntimeError("Cannot concatenate " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
	}
	
	@Override
	public Primitive<?> mul(Primitive<?> other)
	{
		if (other.type == Type.NUMBER)
		{
			NumberPrimitive multi = other.getValue(Type.NUMBER);
			
			return new StringPrimitive(multiply(this.value, multi.getValue()), context);
		}
		
		throw new RuntimeError("Right hand of operation '*' must be a number");
	}
	
	@Override
	public BooleanPrimitive equal(Primitive<?> other)
	{
		if (other.type != Type.STRING)
		{
			return new BooleanPrimitive(false, context);
		}
		
		StringPrimitive value = other.getValue(Type.STRING);
		
		return new BooleanPrimitive(this.value.equals(value.getValue()), context);
	}
	
	public static String multiply(String string, double iterations)
	{
		StringBuilder value = new StringBuilder();
		
		for (int i = 0; i < iterations; i++)
		{
			value.append(string);
		}
		
		return value.toString();
	}
}
