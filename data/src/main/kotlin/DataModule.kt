import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val dataBaseModule = module {
    single<HikariConfig> { HikariConfig().apply {
        jdbcUrl = "jdbc:mysql://db:3306/ktor_task_tester"
        driverClassName = "com.mysql.cj.jdbc.Driver"
        username = "service"
        password = "3277122228"
        maximumPoolSize = 10
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    } }
    single<HikariDataSource> { HikariDataSource(get()) }
    single<Database> { Database.connect(get<HikariDataSource>()) }
}