package me.sullivan.glasslang.interpreter.primitives.parsing;

import me.sullivan.glasslang.interpreter.primitives.types.Primitive;

public interface ParseMethod
{
    Primitive<?> parse();
}
