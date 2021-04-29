package me.sullivan.glasslang.interpreter.primitives.types;

public enum Type
{
    STRING("str"), NUMBER("num"), VOID(null), FUNCTION("func"), BOOLEAN("bool"), LIST("list"), TYPE("type"),
    DICTIONARY("dict");

    final String name;

    Type(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
