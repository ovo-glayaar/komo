package com.komo

import com.komo.model.Api
import com.komo.model.ApiResponse
import com.komo.model.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.Closeable

interface DAOFacade: Closeable{
    fun init()

    //User
    fun createUser(username: String, password: String, name: String, apiStates: List<Int>)
    fun updateUser(id: Int, username: String, password: String, name: String, token: String, apiStates: List<Int>)
    fun deleteUser(id: Int)
    fun getUser(id: Int): User?
    fun getAllUsers(): List<User>

    //API
    fun createApi(name: String, url: String, codeResponses: List<String>)
    fun updateApi(id: Int, name: String, url: String, codeResponses: List<String>)
    fun deleteApi(id: Int)
    fun getApi(id: Int): Api?
    fun getApiResponse(id: Int): ApiResponse?
    fun getAllApis(): List<Api>
    fun getAllApiResponses(): List<ApiResponse>
}