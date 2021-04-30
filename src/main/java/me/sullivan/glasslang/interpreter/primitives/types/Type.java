package me.sullivan.glasslang.interpreter.primitives.types;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;

import java.util.stream.Stream;

public enum Type
{
    STRING("str"), NUMBER("num"), VOID(null), FUNCTION("func"), BOOLEAN("bool"), LIST("list"), TYPE("type"),
    DICTIONARY("dict");

    final String name;

    Type(String name)
    {
        this.name = name;
    }

    public static Type getBy(String value, Context context)
    {
        for (Type type : values())
        {
            if (type.getName().equals(value))
            {
                return type;
            }
        }

        throw new RuntimeError("No type '" + value + "'", context);
    }

    public String getName()
    {
        return name;
    }
}
