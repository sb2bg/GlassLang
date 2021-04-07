package me.sullivan.glasslang.parser.nodes

import me.sullivan.glasslang.interpreter.Interpreter
import me.sullivan.glasslang.interpreter.primitives.Primitive
import me.sullivan.glasslang.lexer.token.Token

class FunctionDefinitionNode(token: Token, val args: List<Token>, val body: Node) : Node(token)
{
    override val value: Any
        get() = token.value

    override fun toString(): String
    {
        val name = if (token.value != null) token.value else "anonymous-func"
        return "Function($name) , $args => $body"
    }

    override fun visitor(interpreter: Interpreter): Primitive<*>
    {
        return interpreter.visitFuncDefinitionNode(this)
    }
}