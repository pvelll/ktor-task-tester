package security.jwt

import java.util.*

object JwtConfigLoader {
    private val properties: Properties = Properties()

    init {
        val stream = JwtConfigLoader::class.java.getResourceAsStream("/jwt.properties")
        properties.load(stream)
    }

    fun get(key: String): String = properties.getProperty(key)
}