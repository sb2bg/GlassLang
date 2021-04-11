package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;

public class BooleanPrimitive extends Primitive<Boolean> {
	
	public BooleanPrimitive(Boolean value, Context context) 
	{
		super(value, Type.BOOLEAN, context);
	}

	@Override
	public boolean isTrue()
	{
		return value;
	}
	
	@Override
	public BooleanPrimitive not()
	{
		return new BooleanPrimitive(!value, context);
	}
	
	@Override
	public BooleanPrimitive and(Primitive<?> other)
	{
		if (other.type != Type.BOOLEAN)
		{
			throw new RuntimeError("Right hand of operation 'and' must be a boolean", context);
		}
		
		BooleanPrimitive value = other.getValue(Type.BOOLEAN);
		
		return new BooleanPrimitive(this.value && value.getValue(), context);
	}
	
	@Override
	public BooleanPrimitive or(Primitive<?> other)
	{
		if (other.type != Type.BOOLEAN)
		{
			throw new RuntimeError("Right hand of operation 'or' must be a boolean", context);
		}
		
		BooleanPrimitive value = other.getValue(Type.BOOLEAN);
		
		return new BooleanPrimitive(this.value || value.getValue(), context);
	}
	
	@Override
	public Primitive<?> add(Primitive<?> other)
	{
		if (other.type != Type.STRING)
		{
			throw new RuntimeError("Right hand of operation '+' must be a string", context);
		}
		
		StringPrimitive value = other.getValue(Type.STRING);
		
		return new StringPrimitive(this.value + value.getValue(), context);
	}
}