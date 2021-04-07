package me.sullivan.glasslang.parser.nodes

import me.sullivan.glasslang.interpreter.Interpreter
import me.sullivan.glasslang.interpreter.primitives.Primitive
import me.sullivan.glasslang.lexer.token.Token

class AssignmentNode(token: Token, override val value: Node) : Node(token) {

    override fun toString(): String {
        return "Assignment(${token.value}, $value)"
    }

    override fun visitor(interpreter: Interpreter): Primitive<*> {
        return interpreter.visitAssignmentNode(this)
    }
}