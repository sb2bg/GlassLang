package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;

import java.util.HashMap;

public class NumberPrimitive extends Primitive<Double>
{
    public NumberPrimitive(double value, Context context)
    {
        super(value, Type.NUMBER, context, new HashMap<>());
    }

    @Override
    public Primitive<?> add(Primitive<?> other)
    {
        return switch (other.getType())
                {
                    case NUMBER -> new NumberPrimitive(value + Primitive.<NumberPrimitive>cast(other).getValue(), context);
                    case STRING -> new StringPrimitive(doubleToString(value) + other.getValue(), context);
                    default -> throw new RuntimeError("Right hand of operation '+' must be number or string", context);
                };
    }

    @Override
    public NumberPrimitive min(Primitive<?> other)
    {
        final NumberPrimitive otherValue = other.getValue(Type.NUMBER);
        return new NumberPrimitive(value - otherValue.getValue(), context);
    }

    @Override
    public NumberPrimitive div(Primitive<?> other)
    {
        final NumberPrimitive otherValue = other.getValue(Type.NUMBER);
        return new NumberPrimitive(this.value / otherValue.getValue(), context);
    }

    @Override
    public Primitive<?> mul(Primitive<?> other)
    {
        return switch (other.getType())
                {
                    case NUMBER -> new NumberPrimitive(value * Primitive.<NumberPrimitive>cast(other).getValue(), context);
                    case STRING -> new StringPrimitive(Primitive.<StringPrimitive>cast(other).getValue().repeat(value.intValue()), context);
                    default -> throw new RuntimeError("Right hand of operation '+' must be number or string", context);
                };
    }

    @Override
    public Primitive<?> mod(Primitive<?> other)
    {
        NumberPrimitive otherValue = other.getValue(Type.NUMBER);
        return new NumberPrimitive(value % otherValue.getValue(), context);
    }

    @Override
    public NumberPrimitive pow(Primitive<?> other)
    {
        NumberPrimitive otherValue = other.getValue(Type.NUMBER);
        return new NumberPrimitive(Math.pow(value, otherValue.getValue()), context);
    }

    @Override
    public BooleanPrimitive less(Primitive<?> other)
    {
        NumberPrimitive otherValue = other.getValue(Type.NUMBER);
        return new BooleanPrimitive(value < otherValue.getValue(), context);
    }

    @Override
    public BooleanPrimitive lessEqual(Primitive<?> other)
    {
        NumberPrimitive otherValue = other.getValue(Type.NUMBER);
        return new BooleanPrimitive(value <= otherValue.getValue(), context);
    }

    @Override
    public BooleanPrimitive greater(Primitive<?> other)
    {
        NumberPrimitive otherValue = other.getValue(Type.NUMBER);
        return new BooleanPrimitive(value > otherValue.getValue(), context);
    }

    @Override
    public BooleanPrimitive greaterEqual(Primitive<?> other)
    {
        NumberPrimitive otherValue = other.getValue(Type.NUMBER);
        return new BooleanPrimitive(this.value >= otherValue.getValue(), context);
    }

    @Override
    public BooleanPrimitive notEqual(Primitive<?> other)
    {
        return equal(other).not();
    }

    // TODO fix num to str method
    public static String doubleToString(Double value)
    {
        String stringVal = value.toString();
        return value.toString();
        //return value % 1 == 0 ? stringVal.substring(0, stringVal.length() - 2) : stringVal;
    }

    @Override
    public String toString()
    {
        return doubleToString(value);
    }
}
