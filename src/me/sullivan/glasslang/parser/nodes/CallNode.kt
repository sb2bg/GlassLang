package me.sullivan.glasslang.parser.nodes

import me.sullivan.glasslang.interpreter.Interpreter
import me.sullivan.glasslang.interpreter.primitives.Primitive
import me.sullivan.glasslang.lexer.token.Token

class CallNode(token: Token, private val atom: Node, val argNodes: List<Node>) : Node(token)
{
    override val value: String
        get() = atom.token.value

    override fun toString(): String
    {
        return "CallNode($value, $argNodes)"
    }

    override fun visitor(interpreter: Interpreter): Primitive<*>
    {
        return interpreter.visitCallNode(this)
    }

    fun nodeToCall(): Node
    {
        return atom
    }
}
