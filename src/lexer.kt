class Lexer(val text: String) {
    private var pos: Int = 0
    private var currentChar: Char? = null

    init {
        currentChar = text[pos]
    }

    public fun nextToken(): Token {
        var value: String
        var type: TokenType?

        while (currentChar != null) {
            if (currentChar!!.isWhitespace()) {
                skip()
                continue
            }
            if (currentChar!!.isDigit()) {
                return Token(TokenType.NUMBER, number())
            }

            type = null
            value = "$currentChar"

            when (currentChar) {
                '+' -> type = TokenType.PLUS
                '-' -> type = TokenType.MINUS
                '/' -> type = TokenType.DIV
                '*' -> type = TokenType.MUL
                '(' -> type = TokenType.LPAREN
                ')' -> type = TokenType.RPAREN
            }

//            if (type != null) {
//                forward()
//                return Token(type, value)
//            }

            type?.let {
                forward()
                return Token(it, value)
            }

            throw InterpreterException("invalid token")
        }
        return Token(TokenType.EOL, "")
    }

    private fun forward() {
        pos += 1
        if (pos > text.length - 1) {
            currentChar = null
        }
        else {
            currentChar = text[pos]
        }
    }

    private fun skip() {
        while ((currentChar != null) && currentChar!!.isWhitespace()){
            forward()
        }
    }

    private fun number(): String {
        var result = arrayListOf<Char>()
        while ((currentChar != null) && (currentChar!!.isDigit())) {
            result.add(currentChar!!)
            forward()
        }
        return result.joinToString("")
    }

}

fun main(args: Array<String>) {
    val lexer = Lexer("2 + 2")
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
}

