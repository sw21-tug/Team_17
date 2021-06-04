package com.example.loginsesame.helper

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.regex.Matcher
import java.util.regex.Pattern

class LogAssert {
    private var logText: CharSequence = ""

    private val logs: String
        get() {
            val logcat: Process
            val log = StringBuilder()
            try {
                logcat = Runtime.getRuntime().exec(arrayOf("logcat", "-d"))
                val br = BufferedReader(InputStreamReader(logcat.inputStream), 4 * 1024)
                var line: String?
                val separator = System.getProperty("line.separator")
                while (br.readLine().also { line = it } != null) {
                    log.append(line)
                    log.append(separator)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return log.toString()
        }


    @Throws(Exception::class)
    fun assertLogsExist(assertPatterns: Array<String>) {
        logText = logs
        for (patternString in assertPatterns) {
            val pattern: Pattern = Pattern.compile(patternString)
            val matcher: Matcher = pattern.matcher(logText)
            if (!matcher.find()) {
                throw Exception("Log [$patternString] is missing")
            }
        }
    }

    private fun clearLog() {
        try {
            ProcessBuilder()
                    .command("logcat", "-c")
                    .redirectErrorStream(true)
                    .start()
        } catch (e: IOException) {
        }
    }

    init {
        clearLog()
    }
}
