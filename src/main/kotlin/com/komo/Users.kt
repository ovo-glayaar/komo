package com.komo

import com.komo.model.ApiResponse
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Table

object Users: IntIdTable(){
    val username = varchar("username", 50)
    val password = varchar("password", 50)
    val name = varchar("name", 50)
}

object UserApiStates: Table() {

    val user = reference("user", Users).primaryKey(0)
    val apiResponse = reference("apiResponse", ApiResponses).primaryKey(1)

}

object UserTokens: IntIdTable() {

    val user = reference("userId", Users)
    val token = varchar("token", 250)
}