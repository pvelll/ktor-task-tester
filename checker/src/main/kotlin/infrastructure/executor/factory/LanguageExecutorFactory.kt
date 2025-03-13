package com.sushkpavel.infrastructure.executor.factory

import com.sushkpavel.domain.executor.LanguageExecutor
import com.sushkpavel.infrastructure.executor.CppExecutor
import com.sushkpavel.infrastructure.executor.JavaExecutor
import com.sushkpavel.infrastructure.executor.KotlinExecutor
import com.sushkpavel.infrastructure.executor.PythonExecutor

class LanguageExecutorFactory {
    private val executors = mapOf(
        Languages.CPP.lang to ::CppExecutor,
        Languages.JAVA.lang to ::JavaExecutor,
        Languages.KOTLIN.lang to ::KotlinExecutor,
        Languages.PYTHON.lang to ::PythonExecutor
    )

    fun build(language: String): LanguageExecutor {
        return executors[language]?.invoke() ?: throw IllegalArgumentException("Unsupported language: $language")
    }
}

