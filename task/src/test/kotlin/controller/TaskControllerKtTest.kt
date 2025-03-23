package controller

import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class TaskControllerKtTest {

    @Test
    fun testGetTask() = testApplication {
        application {
            TODO("Add the Ktor module for the test")
        }
        client.get("/task").apply {
            TODO("Please write your test here")
        }
    }
}