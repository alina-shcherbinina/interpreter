abstract class Node

interface NodeVisitor {

    fun visit(node: Node): Double

}

class Number(val token: Token): Node() {

    override fun toString(): String {
        return "Number ($token)"
    }

}

class BinOp(val left: Node, val op: Token, val right: Node): Node() {

    override fun toString(): String {
        return "BinOp${op.value} ($left, $right)"
    }

}

class UnaryOp (val op: Token, val expr: Node): Node() {

    override fun toString(): String {
        return "UnaryOp${op.value} ($expr)"
    }

}