package com.komo

import org.jetbrains.exposed.dao.UUIDTable

object Apis: UUIDTable(){
    val name = varchar("name", 50)
    val responses = reference("responses", ApiResponses)
}

object ApiResponses: UUIDTable() {
    val code = varchar("code", 50)
    val response = varchar("response", 250000)
}