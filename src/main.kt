fun main(args: Array<String>) {
    var interpreter: Interpreter
    var parser: Parser
    var text: String

    while (true) {
        print("in> ")
        text = readLine().toString()

        if ((text == "exit") or (text.isEmpty())) {
            break
        }

        interpreter = Interpreter()
        parser = Parser(Lexer(text))
        try {
            interpreter.interpret(parser.parse())
        } catch (e: InterpreterException) {
            System.err.println("\t $e")
        }

    }

    println("Bye, bye!")

}