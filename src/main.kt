fun main(args: Array<String>) {
    var interpreter: Interpreter
    var text: String
    var result: Double

    while (true) {
        print("in> ")
        text = readLine().toString()
        println(": $text")

        if ((text == "exit") or (text.isEmpty())) {
            break
        }

//        interpreter = Interpreter(Lexer(text))
//        try {
//            result = interpreter.expr()
//            println("out> $result")
//        }
//        catch (e: InterpreterException) {
//            System.err.println("out> $e")
//        }

    }

    println("Bye, bye!")

}