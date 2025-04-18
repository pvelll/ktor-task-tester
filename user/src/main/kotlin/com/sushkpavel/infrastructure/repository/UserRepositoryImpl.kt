package com.sushkpavel.infrastructure.repository

import com.sushkpavel.domain.dto.UserDTO
import com.sushkpavel.domain.model.Role
import com.sushkpavel.domain.model.User
import com.sushkpavel.domain.repository.UserRepository
import org.jetbrains.exposed.sql.transactions.transaction
import com.sushkpavel.tasktester.tables.Users
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.Instant

class UserRepositoryImpl(database: Database) : UserRepository {

    init {
        transaction(database) {
            SchemaUtils.create(Users)
        }
    }

    override suspend fun create(user: UserDTO): Int = dbQuery {
        val existingUser = Users.selectAll().where { Users.username eq user.username or (Users.email eq user.email) }.singleOrNull()
        if (existingUser != null) {
            HttpStatusCode.Conflict.value
        } else {
            Users.insert {
                it[username] = user.username
                it[email] = user.email
                it[role] = user.role!!.name
                it[passwordHash] = user.passwordHash
                it[createdAt] = Instant.now()
                it[updatedAt] = Instant.now()
            }
            HttpStatusCode.Created.value
        }
    }



    override suspend fun read(id: Int): User? {
        return dbQuery {
            Users.selectAll().where { Users.userId eq id }
                .map {
                    toUser(it)
                }.singleOrNull()
        }
    }

    override suspend fun update(id: Int, user: User) {
        dbQuery {
            Users.update({ Users.userId eq id }) {
                it[username] = user.username
                it[email] = user.email
                it[passwordHash] = user.passwordHash
                it[updatedAt] = Instant.now()
            }
        }
    }

    override suspend fun delete(id: Int) {
        dbQuery {
            Users.deleteWhere { userId eq id }
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return dbQuery {
            Users.selectAll().where { Users.email eq email }.map {
                toUser(it)
            }.singleOrNull()
        }
    }

    override suspend fun updateRole(id: Int, role: Role) = dbQuery {
        TODO()
    //        Users.update({ Users.userId eq id }) {
//            it[role] = role.name
//            it[updatedAt] = Instant.now()
//        }
    }
    private fun toUser(row: ResultRow): User {
        return User(
            userId = row[Users.userId],
            username = row[Users.username],
            email = row[Users.email],
            passwordHash = row[Users.passwordHash],
            role = Role.valueOf(row[Users.role]),
            createdAt = row[Users.createdAt],
            updatedAt = row[Users.updatedAt]
        )
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
