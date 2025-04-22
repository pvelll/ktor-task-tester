package com.sushkpavel.tasktester.tables

import entities.user.Role
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp

object Users : Table() {
    val userId = integer("user_id").autoIncrement()
    val username = varchar("username", length = 255)
    val email = varchar("email", length = 255).uniqueIndex()
    val passwordHash = varchar("password_hash", length = 255)
    val role = varchar("role", length = 50).default(Role.USER.name)
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp)

    override val primaryKey = PrimaryKey(userId)

    init {
        uniqueIndex(username)
        uniqueIndex(email)
    }
}