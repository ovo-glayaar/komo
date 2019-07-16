package com.komo

import org.jetbrains.exposed.dao.IntIdTable

object Apis: IntIdTable(){
    val name = varchar("name", 50)
    val url = varchar("url", 500)
}

object ApiResponses: IntIdTable() {

    val api = reference("api", Apis)

    val code = varchar("code", 5)
    val type = varchar("type", 25)
    val response = varchar("response", 250000)

}