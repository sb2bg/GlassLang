package me.sullivan.glasslang.parser.nodes

import me.sullivan.glasslang.interpreter.Interpreter
import me.sullivan.glasslang.interpreter.primitives.Primitive

class ListNode(private var list: List<Node>) : Node(null) {

    override fun toString(): String
    {
        return "List[$list]"
    }

    override fun getValue(): List<Node>
    {
        return list
    }

    override fun visitor(interpreter: Interpreter): Primitive<*>
    {
        return interpreter.visitListNode(this)
    }

}