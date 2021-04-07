package me.sullivan.glasslang.parser.nodes

import me.sullivan.glasslang.interpreter.Interpreter
import me.sullivan.glasslang.interpreter.primitives.Primitive
import me.sullivan.glasslang.lexer.token.Token

class ForNode(token: Token, val startValue: Node, val endValue: Node, val step: Node?, val eval: Node) : Node(token) {
	override val value: String
		get() = token.value

	override fun toString(): String {
		return "For(start=$startValue, endValue=$endValue, step=$step, body=$eval)"
	}

	override fun visitor(interpreter: Interpreter): Primitive<*> {
		return interpreter.visitForNode(this)
	}
}
