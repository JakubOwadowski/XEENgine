package xeengine.src.main.logger

import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

class Logger {
    companion object {
        private val logPath = System.getProperty("user.home") + "/Documents/xeengine/logs"
        private var initLog = File("$logPath/init.log")
        private var gameLog = File("$logPath/game.log")
        private var errorLog = File("$logPath/game.log")

        init {
            val logDirectory = File(logPath)
            if (!logDirectory.exists()) {
                logDirectory.mkdirs()
            }
            initLog = File("$logPath/init.log")
            gameLog = File("$logPath/game.log")
            errorLog = File("$logPath/error.log")
            if (!initLog.exists()) {
                initLog.createNewFile()
            }
            if (!gameLog.exists()) {
                gameLog.createNewFile()
            }
            if (!errorLog.exists()) {
                errorLog.createNewFile()
            }
        }

        private fun getCurrentTimestamp(): String {
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return currentDateTime.format(formatter)
        }

        fun cleanLogs() {
            initLog.writeText("")
            gameLog.writeText("")
            errorLog.writeText("")
        }

        fun initLog(message: String, clazz: KClass<*>) {
            initLog.appendText("[${clazz.qualifiedName} ${getCurrentTimestamp()}] $message\n")
        }

        fun gameLog(message: String, clazz: KClass<*>) {
            gameLog.appendText("[${clazz.qualifiedName} ${getCurrentTimestamp()}] $message\n")
        }

        fun errorLog(message: String, clazz: KClass<*>) {
            errorLog.appendText("[${clazz.qualifiedName} ${getCurrentTimestamp()}] $message\n")
        }
    }
}