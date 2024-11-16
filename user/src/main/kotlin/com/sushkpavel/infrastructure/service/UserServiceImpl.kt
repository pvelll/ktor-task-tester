package com.sushkpavel.infrastructure.service

import com.sushkpavel.domain.model.User
import com.sushkpavel.domain.service.UserService
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import com.sushkpavel.domain.service.UserService.Users

class UserServiceImpl(database: Database) : UserService {

    init {
        transaction(database) {
            SchemaUtils.create(Users)
        }
    }

    override suspend fun create(user: User): Int = dbQuery {
        Users.insert {
            it[username] = user.username
            it[email] = user.email
            it[passwordHash] = user.passwordHash
        }[Users.userId]
    }

    override suspend fun read(id: Int): User? {
        return dbQuery {
            Users.selectAll().where { Users.userId eq id }
                .map {
                    User(
                        it[Users.userId],
                        it[Users.username],
                        it[Users.email],
                        it[Users.passwordHash],
                        it[Users.createdAt],
                        it[Users.updatedAt]
                    )
                }
                .singleOrNull()
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

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}


