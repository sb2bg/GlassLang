package me.sullivan.glasslang.parser.nodes

import me.sullivan.glasslang.interpreter.Interpreter
import me.sullivan.glasslang.interpreter.primitives.Primitive
import me.sullivan.glasslang.lexer.token.Token

abstract class Node(var token: Token)
{
    abstract val value: Any

    abstract override fun toString(): String
    abstract fun visitor(interpreter: Interpreter): Primitive<*>?
}