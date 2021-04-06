package me.sullivan.glasslang.interpreter.primitives;

import java.util.List;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.parser.nodes.Node;

public abstract class Primitive<T> {

	protected Context context;
	protected T value;
	protected Type type;

	public Primitive(T value, Type type, Context context)
	{
		this.value = value;
		this.type = type;
		this.context = context;
	}

	public T getValue()
	{
		return value;
	}
	
	
	public Primitive<?> add(Primitive<?> other)
	{
		throw new RuntimeError("Cannot add " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
	}
	
	public Primitive<?> min(Primitive<?> other)
	{
		throw new RuntimeError("Cannot subtract " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
	}
	
	public Primitive<?> div(Primitive<?> other)
	{
		throw new RuntimeError("Cannot divide " + getClass().getSimpleName() + " with " + other.getClass().getSimpleName());
	}
	
	public Primitive<?> mul(Primitive<?> other)
	{
		throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
	}
	
	public Primitive<?> pow(Primitive<?> other)
	{
		throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
	}
	
	public Primitive<?> less(Primitive<?> other)
	{
		throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
	}
	
	public Primitive<?> lessEqual(Primitive<?> other)
	{
		throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
	}
	
	public Primitive<?> greater(Primitive<?> other)
	{
		throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
	}
	
	public Primitive<?> greaterEqual(Primitive<?> other)
	{
		throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
	}
	
	public Primitive<?> equal(Primitive<?> other)
	{
		throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
	}
	
	public Primitive<?> notEqual(Primitive<?> other)
	{
		throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
	}
	
	public Primitive<?> and(Primitive<?> other)
	{
		throw new RuntimeError("Cannot use 'and' on " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
	}
	
	public Primitive<?> or(Primitive<?> other)
	{
		throw new RuntimeError("Cannot use 'or' on " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
	}
	
	public Primitive<?> not()
	{
		throw new RuntimeError("Cannot use 'not' on " + getClass().getSimpleName() + "");
	}
	
	public boolean isTrue()
	{
		throw new RuntimeError("Cannot check if " + getClass().getSimpleName() + " is true");
	}
	
	public Primitive<?> call(List<Node> argNodes)
	{
		throw new RuntimeError("Cannot call " + getClass().getSimpleName());
	}
	
	@SuppressWarnings("unchecked")
	public<CT extends Primitive<?>> CT getValue(Type type)
	{
		if (type != this.type)
		{
			throw new RuntimeError("Unexpected datatype " + type);
		}

		return (CT) this;
	}
	
	public String toString() 
	{
		return value != null ? value.toString() : "void";
	}
}
