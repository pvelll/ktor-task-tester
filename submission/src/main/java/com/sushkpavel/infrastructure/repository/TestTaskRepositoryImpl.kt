package com.sushkpavel.infrastructure.repository

import com.sushkpavel.domain.dto.SubmissionRequest
import com.sushkpavel.domain.model.TestResult
import com.sushkpavel.domain.repo.TestTaskRepository
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json


class TestTaskRepositoryImpl(private val client: HttpClient) : TestTaskRepository {
    override suspend fun testTask(submissionRequest: SubmissionRequest): TestResult{
        val response =  client.post("http://localhost:8084/check-solution") {
            setBody(submissionRequest)
        }.bodyAsText()
        return Json.decodeFromString<TestResult>(response)
    }
}