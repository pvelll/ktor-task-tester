package com.sushkpavel.domain.repo

import com.sushkpavel.domain.model.CodeTest

interface CodeTestRepository {
    suspend fun getTestByTaskId(id: String) : CodeTest?
}