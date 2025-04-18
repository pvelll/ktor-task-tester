package tables

import com.sushkpavel.tasktester.tables.Users
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp

object Submissions : Table() {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(Users.userId, onDelete = ReferenceOption.CASCADE)
    val taskId = long("task_id").references(Tasks.id, onDelete = ReferenceOption.CASCADE)
    val code = text("code")
    val language = varchar("language", length = 50)
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(id)
    }

}