package com.komo.model

import com.komo.ApiResponses
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class ApiResponse(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<ApiResponse>(ApiResponses)

    var api by Api referencedOn ApiResponses.api

    var code by ApiResponses.code
    var type by ApiResponses.type
    var response by ApiResponses.response
}

