package com.komo

import org.jetbrains.exposed.dao.UUIDTable
import org.jetbrains.exposed.sql.Table

object Users: Table(){
    val id = varchar("id",100).primaryKey()
    val username = varchar("username", 50)
    val password = varchar("password", 50)
    val name = varchar("name", 50)
    val userToken = reference("userToken", UserTokens)
    val userApiState = reference("userApiState", UserApiStates)
}

object UserApiStates: UUIDTable() {
    val apiId = UserTokens.varchar("token", 250)
    val responseCode = varchar("token", 250)
}

object UserTokens: UUIDTable() {
    val token = varchar("token", 250)
}