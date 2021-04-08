package me.sullivan.glasslang.parser.nodes

import me.sullivan.glasslang.interpreter.Interpreter
import me.sullivan.glasslang.interpreter.primitives.Primitive
import me.sullivan.glasslang.lexer.token.Token

class ImportNode(token: Token) : Node(token)
{
    override val value: String
        get() = token.value

    override fun toString(): String
    {
        return "Import(${token.value})"
    }

    override fun visitor(interpreter: Interpreter): Primitive<*>?
    {
        return interpreter.visitImportNode(this)
    }
}