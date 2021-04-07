package me.sullivan.glasslang.parser.nodes

import me.sullivan.glasslang.interpreter.Interpreter
import me.sullivan.glasslang.interpreter.primitives.Primitive
import me.sullivan.glasslang.lexer.token.Token

class NumberNode(token: Token) : Node(token)
{
    override val value: Double
        get() = token.value.toDouble()

    override fun toString(): String
    {
        return "Number($value)"
    }

    override fun visitor(interpreter: Interpreter): Primitive<*>
    {
        return interpreter.visitNumberNode(this)
    }
}
