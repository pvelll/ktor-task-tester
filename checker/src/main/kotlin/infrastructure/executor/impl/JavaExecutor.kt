package com.sushkpavel.infrastructure.executor.impl

import com.sushkpavel.infrastructure.executor.BaseTaskExecutor
import java.nio.file.Path

class JavaExecutor : BaseTaskExecutor() {
    override val languageName = "java"
    override val sourceExtension = "java"
    override val needCompilation = true

    override fun preprocessCode(code: String, className: String) =
        code.replace("public class Solution", "public class $className")

    override val compileCommand = { source: String, _: Path ->
        listOf("javac", source)
    }

    override val executeCommand = { className: String, dir: Path ->
        listOf("java", "-cp", dir.toString(), className)
    }
}