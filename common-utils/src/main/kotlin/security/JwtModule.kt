package security

import com.sushkpavel.tasktester.security.JwtConfig
import org.koin.dsl.module

val jwtConfig = module {
    single {
        JwtConfig(
            domain = JwtConfigLoader.get("jwt.domain"),
            audience = JwtConfigLoader.get("jwt.audience"),
            realm = JwtConfigLoader.get("jwt.realm"),
            secret = JwtConfigLoader.get("jwt.secret")
        )
    }
}