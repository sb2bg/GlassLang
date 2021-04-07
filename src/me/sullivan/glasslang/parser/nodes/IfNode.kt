package me.sullivan.glasslang.parser.nodes

import me.sullivan.glasslang.interpreter.Interpreter
import me.sullivan.glasslang.interpreter.primitives.Primitive
import me.sullivan.glasslang.lexer.token.Token

class IfNode(token: Token, private val cases: Map<Node, Node>, val elseCase: Node?) : Node(token) {

    override val value: Map<Node, Node>
        get() = cases

    override fun toString(): String {
        return "If($cases) else => $elseCase"
    }

    override fun visitor(interpreter: Interpreter): Primitive<*> {
        return interpreter.visitIfNode(this)
    }
}