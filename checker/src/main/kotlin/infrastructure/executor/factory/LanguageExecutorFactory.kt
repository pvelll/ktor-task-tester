package com.sushkpavel.infrastructure.executor.factory

import com.sushkpavel.domain.executor.LanguageExecutor
import com.sushkpavel.infrastructure.executor.CppExecutor
import com.sushkpavel.infrastructure.executor.JavaExecutor

class LanguageExecutorFactory {
    fun build(language : String) : LanguageExecutor? {
        return when(language){
            Languages.CPP.lang -> CppExecutor()
            Languages.JAVA.toString().lowercase() -> JavaExecutor()
            else -> null
        }

    }
}

