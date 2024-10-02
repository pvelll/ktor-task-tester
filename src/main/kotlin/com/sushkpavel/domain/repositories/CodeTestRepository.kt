package com.sushkpavel.domain.repositories

import com.sushkpavel.domain.models.CodeTest

interface CodeTestRepository {
    suspend fun getTestByTaskId(id: String) : CodeTest?
}