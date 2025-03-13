package com.sushkpavel.infrastructure.executor

import com.sushkpavel.domain.executor.LanguageExecutor
import com.sushkpavel.domain.model.*
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

class KotlinExecutor : BaseLanguageExecutor() {
    override val languageName = "kotlin"
    override val sourceExtension = "kt"
    override val needCompilation = true

    override fun preprocessCode(code: String, className: String) =
        code.replace("class Solution", "class $className")

    override val compileCommand = { source: String, dir: Path ->
        val jarName = "${source.removeSuffix(".kt")}.jar"
        println("Compiling to: ${dir.resolve(jarName)}")
        listOf("kotlinc", source, "-include-runtime", "-d", jarName)
    }

    override val executeCommand = { className: String, dir: Path ->
        listOf("java", "-jar", dir.resolve("$className.jar").toString())
    }
}