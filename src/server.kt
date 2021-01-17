import java.io.File
import java.net.ServerSocket
import java.net.SocketTimeoutException

class Server (private val port: Int) {
    private val serverSocket = ServerSocket(port)

    fun start() {
        val interperter = Interpreter()
        Logger.attach(ConsoleHandler())
        Logger.attach(FileHandler(File("log.txt")))

        while (true) {
            Logger.info("Wait for new connection")
            val clientSocket = serverSocket.accept()
            clientSocket.soTimeout = 5 * 60000
            Logger.info("Process new client ${clientSocket.localAddress}")
            val inStream = clientSocket.getInputStream().bufferedReader()
            val outStream = clientSocket.getOutputStream().bufferedWriter()
            while (!clientSocket.isOutputShutdown) {
                try {
                    Logger.info("Wait for a new message")
                    val data =inStream.readLine()
                    if (data != null){
                        Logger.info(data)
                        val result = interperter.interpret(Parser(Lexer(data)).expr())
                        outStream.write("Result = $result\n")
                        outStream.flush()
                    } else {
                        Logger.info("Client closed connection")
                        break
                    }
                }
                catch (e: SocketTimeoutException) {
                    Logger.warning("Disconnect client due to timeout")
                    clientSocket.close()
                    break
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    Server(2222).start()
}