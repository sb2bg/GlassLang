package me.sullivan.glasslang.interpreter.primitives.parsing;

import me.sullivan.glasslang.interpreter.primitives.Primitive;

public interface ParseMethod
{
    Primitive<?> parse();
}
