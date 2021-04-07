package me.sullivan.glasslang.parser.nodes

import me.sullivan.glasslang.interpreter.Interpreter
import me.sullivan.glasslang.interpreter.primitives.Primitive
import me.sullivan.glasslang.lexer.token.Token

class UnaryOpNode(token: Token, val node: Node) : Node(token) {
	override val value: Token
		get() = token

	override fun toString(): String {
		return "UnaryOp(" + value.type + ", " + node + ")"
	}

	override fun visitor(interpreter: Interpreter): Primitive<*> {
		return interpreter.visitUnaryOpNode(this)
	}
}
