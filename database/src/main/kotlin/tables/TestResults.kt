package tables

import com.sushkpavel.tasktester.tables.Users
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp


object TestResults : Table() {
        val id = integer("id").autoIncrement()
        val userId = integer("user_id").references(Users.userId, onDelete = ReferenceOption.CASCADE)
        val submissionId = integer("submission_id").references(Submissions.id, onDelete = ReferenceOption.CASCADE)
        val taskId = long("task_id").references(Tasks.id, onDelete = ReferenceOption.CASCADE) // ✅ Исправлено
        val actualResult = text("actual_result")
        val success = bool("success")
        val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)

        override val primaryKey = PrimaryKey(id)

        init {
            uniqueIndex(id)
        }

    }