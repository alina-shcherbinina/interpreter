class Lexer(val text: String) {
    private var pos: Int = 0
    private var currentChar: Char? = null
    private var buffer = ""
    private var secondBuffer = ""

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
                ';' -> type = TokenType.EOL
                '.' -> type = TokenType.EXIT
                else ->{
                    var pair = collectChars(currentChar!!)
                    type = pair.first
                    value = pair.second

                }
            }

            type?.let {
                forward()
                return Token(it, value)
            }

            if(type == null){
                forward()
                continue
            }

            throw InterpreterException("invalid token")
        }
        return Token(TokenType.EOL, "")
    }

    private  fun collectChars(currentChar: Char): Pair<TokenType?, String> {

        if (secondBuffer.isNotEmpty()){
            buffer = secondBuffer
            secondBuffer = ""
        }

        if(buffer.isNotBlank() && currentChar == ':'){
            secondBuffer = ":"
            return Pair(TokenType.VAR, buffer)
        }

        buffer  += currentChar

        when(buffer){
            "BEGIN" -> {
                buffer =""
                return Pair(TokenType.BEGIN, "BEGIN")
            }
            "END" -> {
                buffer =""
                return Pair(TokenType.END, "END")
            }
            ":=" -> {
                buffer =""
                return Pair(TokenType.ASSIGN, ":=")
            }
        }
        return Pair(null, "")
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
    val lexer = Lexer("BEGIN\n hello := 2 + 2; \nEND . ;")
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())

}

