package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.parser.nodes.Node;

import java.util.List;
import java.util.Objects;

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
		throw new RuntimeError("Cannot add " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
	}

	public Primitive<?> min(Primitive<?> other)
	{
		throw new RuntimeError("Cannot subtract " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
	}

	public NumberPrimitive div(Primitive<?> other)
	{
		throw new RuntimeError("Cannot divide " + getClass().getSimpleName() + " with " + other.getClass().getSimpleName(), context);
	}

	public Primitive<?> mul(Primitive<?> other)
	{
		throw new RuntimeError("Cannot multiply " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
	}

	public Primitive<?> mod(Primitive<?> other)
	{
		throw new RuntimeError("Cannot mod " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
	}

	public NumberPrimitive pow(Primitive<?> other)
	{
		throw new RuntimeError("Cannot power " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
	}

	public Primitive<?> less(Primitive<?> other)
	{
		throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
	}

	public BooleanPrimitive lessEqual(Primitive<?> other)
	{
		throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
	}

	public BooleanPrimitive greater(Primitive<?> other)
	{
		throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
	}

	public BooleanPrimitive greaterEqual(Primitive<?> other)
	{
		throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
	}

	public BooleanPrimitive equal(Primitive<?> other)
	{
		return new BooleanPrimitive(this.equals(other), context);
	}

	public BooleanPrimitive notEqual(Primitive<?> other)
	{
		throw new RuntimeError("Cannot compare " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
	}

	public BooleanPrimitive and(Primitive<?> other)
	{
		throw new RuntimeError("Cannot use 'and' on " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
	}

	public BooleanPrimitive or(Primitive<?> other)
	{
		throw new RuntimeError("Cannot use 'or' on " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName(), context);
	}

	public BooleanPrimitive not()
	{
		throw new RuntimeError("Cannot use 'not' on " + getClass().getSimpleName() + "", context);
	}

	public boolean isTrue()
	{
		throw new RuntimeError("Cannot check if " + getClass().getSimpleName() + " is true", context);
	}

	public Primitive<?> call(List<Node> argNodes)
	{
		throw new RuntimeError("Cannot call " + getClass().getSimpleName(), context);
	}

	@SuppressWarnings("unchecked")
	public<CT extends Primitive<?>> CT getValue(Type type)
	{
		if (type != this.type)
		{
			throw new RuntimeError("Expected datatype " + type, context);
		}

		return (CT) this;
	}

	public String toString()
	{
		return value != null ? value.toString() : "void";
	}

	public Type getType()
	{
		return type;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		Primitive<?> primitive = (Primitive<?>) o;
		return value.equals(primitive.value) && type == primitive.type;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(value, type);
	}
}
