package tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp

object TestCases : Table() {
    val id = integer("id").autoIncrement()
    val taskId = long("task_id").references(Tasks.id, onDelete = ReferenceOption.CASCADE)
    val input = text("input")
    val expOutput = text("expected_output")
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(id)
    }

}