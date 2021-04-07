package me.sullivan.glasslang.parser.nodes

import me.sullivan.glasslang.interpreter.Interpreter
import me.sullivan.glasslang.interpreter.primitives.Primitive
import me.sullivan.glasslang.lexer.token.Token

class WhileNode(token: Token, val condition: Node, override val value: Node) : Node(token) {

	override fun toString(): String {
		return "While($condition) => $value"
	}

	override fun visitor(interpreter: Interpreter): Primitive<*> {
		return interpreter.visitWhileNode(this)
	}
}
