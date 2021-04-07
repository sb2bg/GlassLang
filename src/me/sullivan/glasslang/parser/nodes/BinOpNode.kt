package me.sullivan.glasslang.parser.nodes

import me.sullivan.glasslang.interpreter.Interpreter
import me.sullivan.glasslang.interpreter.primitives.Primitive
import me.sullivan.glasslang.lexer.token.Token

class BinOpNode(val left: Node, token: Token, val right: Node) : Node(token) {

    override val value: Token
        get() = token

    override fun toString(): String {
        return "BinOp($left, ${value.type}, $right)"
    }

    override fun visitor(interpreter: Interpreter): Primitive<*> {
        return interpreter.visitBinOp(this)
    }
}
