package tables

import com.sushkpavel.tasktester.entities.task.Difficulty
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object Tasks : Table() {
    val id = long("id").autoIncrement()
    val title = varchar("title", 64)
    val description = text("description")
    val difficulty = enumerationByName("difficulty", 50, Difficulty::class)
    val examples = text("examples")
    val createdAt = timestamp("created_at").defaultExpression(org.jetbrains.exposed.sql.javatime.CurrentTimestamp)
    val updatedAt = timestamp("updated_at").defaultExpression(org.jetbrains.exposed.sql.javatime.CurrentTimestamp)
    override val primaryKey = PrimaryKey(id)
}