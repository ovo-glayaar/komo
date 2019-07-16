package com.komo.model

import com.komo.UserTokens
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class UserToken(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserToken>(UserTokens)

    var user by User referencedOn UserTokens.user
    var token by UserTokens.token
}
