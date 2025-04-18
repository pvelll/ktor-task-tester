package tables

import com.sushkpavel.tasktester.tables.Users
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant
import java.time.temporal.ChronoUnit

object Tokens : Table() {
    val tokenId = integer("token_id").autoIncrement()
    val userId = integer("user_id").references(Users.userId, onDelete = ReferenceOption.CASCADE)
    val token = varchar("token", 512).uniqueIndex()
    val createdAt = timestamp("created_at").defaultExpression(org.jetbrains.exposed.sql.javatime.CurrentTimestamp)
    val expiresAt = timestamp("expires_at").clientDefault { Instant.now().plus(30, ChronoUnit.DAYS) }

    override val primaryKey = PrimaryKey(tokenId)
}