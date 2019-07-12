package com.komo

import com.komo.model.Api
import com.komo.model.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.Closeable

interface DAOFacade: Closeable{
    fun init()

    //User
    fun createUser(user: User)
    fun updateUser(user: User)
    fun deleteUser(id: String)
    fun getUser(id: String): User?
    fun getAllUsers(): List<User>

    //API
    fun createApi(api: Api)
    fun updateApi(api: Api)
    fun deleteApi(id: String)
    fun getApi(id: String): Api?
    fun getApiByCode(id: String, code: String): Api?
    fun getAllApis(): List<Api>
}