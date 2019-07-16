package com.komo.model

import com.google.gson.annotations.SerializedName
import com.komo.ApiResponses
import com.komo.Apis
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class Api(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Api>(Apis)

    var name by Apis.name
    var url by Apis.url
}
