package me.sullivan.glasslang.parser.nodes

import me.sullivan.glasslang.interpreter.Interpreter
import me.sullivan.glasslang.interpreter.primitives.Primitive
import me.sullivan.glasslang.lexer.token.Token

class ListNode(token: Token, private var list: List<Node>) : Node(token) {

    override val value: List<Node>
        get() = list

    override fun toString(): String
    {
        return "List{$list}"
    }

    override fun visitor(interpreter: Interpreter): Primitive<*>
    {
        return interpreter.visitListNode(this)
    }
}