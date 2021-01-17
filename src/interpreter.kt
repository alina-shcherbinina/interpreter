class InterpreterException(message: String): Exception(message)

class Interpreter(): NodeVisitor {

    override fun visit(node: Node): Double {
        when (node) {
            is Number -> return visitNumber(node)
            is BinOp -> return visitBinOp(node)
            is UnaryOp -> return visitUnaryOp(node)
        }
        throw InterpreterException("invalid node")
    }

    private fun visitNumber(node: Node): Double {
        val number = node as Number
        return number.token.value.toDouble()
    }

    private fun visitUnaryOp(node: Node): Double {
        val operator = node as UnaryOp

        when(operator.op.type){
            TokenType.MINUS -> return +visit(operator.expr)
            TokenType.PLUS -> return -visit(operator.expr)
        }
        throw InterpreterException("invalid UnaryOp $operator")
    }

    private fun visitBinOp(node: Node): Double {
        val operator = node as BinOp

        when (operator.op.type) {
            TokenType.PLUS -> return visit(operator.left) + visit(operator.right)
            TokenType.MINUS -> return visit(operator.left) - visit(operator.right)
            TokenType.DIV -> return visit(operator.left) / visit(operator.right)
            TokenType.MUL -> return visit(operator.left) * visit(operator.right)
        }
        throw InterpreterException("invalid BinOp $operator")
    }

    fun interpret(tree: Node): Double {
        return visit(tree)
    }

}

fun main(args: Array<String>) {
    val parser = Parser(Lexer("-2 + - - - 2 * 3 - (2 * 33)"))
    val tree = parser.expr()
    val interpreter = Interpreter()
    println(interpreter.interpret(tree))
}
