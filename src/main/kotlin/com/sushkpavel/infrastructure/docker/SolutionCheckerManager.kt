package com.sushkpavel.infrastructure.docker

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DockerClientBuilder
import com.sushkpavel.domain.repositories.CodeTestRepository
import com.sushkpavel.domain.services.SolutionChecker
import java.util.concurrent.ConcurrentHashMap

class SolutionCheckerManager(private val testRepo: CodeTestRepository) {
    private val dockerClient: DockerClient = DockerClientBuilder.getInstance().build()
    private val containerIds: ConcurrentHashMap<String, String> = ConcurrentHashMap()

    init {
        containerIds["C++"] = createContainer("gcc:latest")
        containerIds["Python"] = createContainer("python:latest")
    }

    private fun createContainer(image: String): String {
        val container = dockerClient.createContainerCmd(image)
            .withCmd("sleep", "infinity")
            .exec()
        dockerClient.startContainerCmd(container.id).exec()
        return container.id
    }

    fun getChecker(language: String): SolutionChecker {
        val containerId = containerIds[language] ?: throw IllegalArgumentException("Unsupported language: $language")
        return when (language) {
            "C++" -> CppSolutionChecker(testRepo, containerId, dockerClient)
//            "Python" -> PythonSolutionChecker(testRepo, containerId, dockerClient)
            else -> throw IllegalArgumentException("Unsupported language: $language")
        }
    }
}