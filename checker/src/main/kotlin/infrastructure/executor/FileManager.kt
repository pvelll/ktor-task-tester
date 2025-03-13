package com.sushkpavel.infrastructure.executor

import java.nio.file.Paths

class FileManager {
    fun cleanup(compilationResult: String) {
        try {
            val (tempDirPath, _) = compilationResult.split("|")
            Paths.get(tempDirPath).toFile().deleteRecursively()
        } catch (e: Exception) {
            println("Cleanup error: ${e.message?.take(254)}")
        }
    }
}