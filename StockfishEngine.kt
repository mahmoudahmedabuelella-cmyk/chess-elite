package com.chess.elite.engine

import android.content.Context
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.concurrent.atomic.AtomicBoolean

class StockfishEngine(private val context: Context) {
    private var process: Process? = null
    private var writer: OutputStreamWriter? = null
    private val isRunning = AtomicBoolean(false)
    private val _engineOutputs = MutableSharedFlow<EngineResponse>(replay = 1)
    val engineOutputs: SharedFlow<EngineResponse> = _engineOutputs

    sealed class EngineResponse {
        data class BestMove(val move: String) : EngineResponse()
        data class Info(val depth: Int, val centipawns: Int?) : EngineResponse()
    }

    suspend fun start() {
        val binaryFile = File(context.filesDir, "stockfish")
        if (!binaryFile.exists()) {
            context.assets.open("stockfish_18_arm64-v8a").use { input ->
                FileOutputStream(binaryFile).use { output -> input.copyTo(output) }
            }
            Runtime.getRuntime().exec(arrayOf("chmod", "755", binaryFile.absolutePath)).waitFor()
        }
        
        process = ProcessBuilder(binaryFile.absolutePath).redirectErrorStream(true).start()
        writer = OutputStreamWriter(process!!.outputStream)
        // Spawn asynchronous coroutines reading the standard command outputs stream
    }

    fun sendCommand(cmd: String) {
        writer?.write(cmd + "\n")
        writer?.flush()
    }
}