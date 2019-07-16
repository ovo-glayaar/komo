package com.komo.model

import com.google.gson.annotations.SerializedName
import com.komo.ApiResponses
import com.komo.UserApiStates
import com.komo.Users
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var username by Users.username
    var password by Users.password
    var name by Users.name
    //val userToken: List<UserToken>,
    //val userApiState: List<UserApiState>

    var apiStates by ApiResponse via UserApiStates

}
                            