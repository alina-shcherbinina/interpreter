import java.util.ArrayList

abstract class Node

interface NodeVisitor {
    fun visit(node: Node): Any?
}

class Number(val token: Token) : Node() {
    override fun toString(): String {
        return "Number ($token)"
    }
}

class Variable(var token: Token) : Node() {
    override fun toString(): String {
        return "Variable ($token)"
    }
}

class Brackets(val expressions: ArrayList<Node>) : Node() {
    override fun toString(): String {
        return "Brackets ($expressions)"
    }
}

class NullOp : Node() {
    override fun toString(): String {
        return "NullOp ()"
    }
}

class AssignOp(var variable: Variable, var node: Node) : Node() {
    override fun toString(): String {
        return "AssignOp ($variable, $node)"
    }
}

class UnaryOp(val op: Token, val expr: Node) : Node() {
    override fun toString(): String {
        return "UnaryOp${op.value} ($expr)"
    }
}

class BinOp(val left: Node, val op: Token, val right: Node) : Node() {
    override fun toString(): String {
        return "BinOp${op.value} ($left, $right)"
    }
}

