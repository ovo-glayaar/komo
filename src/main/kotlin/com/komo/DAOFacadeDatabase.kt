package com.komo

import com.komo.model.Api
import com.komo.model.ApiResponse
import com.komo.model.User
import com.komo.model.UserToken
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DAOFacadeDatabase(val db: Database): DAOFacade {

    override fun init() = transaction(db) {
        SchemaUtils.create(Users)
        SchemaUtils.create(Apis)
    }

    //Users
    override fun createUser(username: String, password: String, name: String, apiStates: List<Int>) = transaction(db) {
        User.new {
            this.username = username
            this.password = password
            this.name = name

            val apiStates = apiStates.map { getApiResponse(it) }.filterNotNull().toList()
            this.apiStates = SizedCollection(apiStates)
        }

        Unit
    }
    override fun updateUser(id: Int, username: String, password: String, name: String, token: String, apiStates: List<Int>) = transaction(db) {

        User.findById(id)?.apply {
            this.username = username
            this.password = password
            this.name = name

            val apiStates = apiStates.map { getApiResponse(it) }.filterNotNull().toList()
            this.apiStates = SizedCollection(apiStates)
        }?.also {
            UserToken.new {
                this.user = it
                this.token = token
            }
        }

        Unit
    }

    fun getResponseByUser(token: String, url: String): String? =  transaction(db) {
        val result = Users.join(UserTokens, JoinType.INNER, additionalConstraint = {Users.id eq UserTokens.user})
            .join(UserApiStates, JoinType.INNER, additionalConstraint = {Users.id eq UserApiStates.user})
            .join(ApiResponses, JoinType.INNER, additionalConstraint = {UserApiStates.apiresponse eq ApiResponses.id})
            .join(Apis, JoinType.INNER, additionalConstraint = {ApiResponses.api eq Apis.id})
            .slice(UserTokens.token, Apis.url, ApiResponses.response)
            .select { UserTokens.token eq token and (Apis.url eq url) }.firstOrNull()

        if(result != null) result[ApiResponses.response] else null

    }

    override fun deleteUser(id: Int) = transaction(db) {
        Users.deleteWhere { Users.id eq id }
        Unit
    }

    override fun getUser(id: Int) = transaction(db) {
        User.findById(id)
    }

    override fun getAllUsers() = transaction(db) {
        User.all().toList()
    }

    //Apis
    override fun createApi(name: String, url: String, codeResponses: List<String>) = transaction(db) {
        val newApi = Api.new {
            this.name = name
            this.url = url
        }

        codeResponses.forEach {
            val responseComp = it.split("@#$")
            ApiResponse.new {
                api = newApi
                code = responseComp[0]
                type = responseComp[1]
                response = responseComp[2]
            }
        }
        Unit
    }

    override fun updateApi(id: Int, name: String, url: String, codeResponses: List<String>) = transaction(db) {
        Api.findById(id)?.apply {
            this.name = name
            this.url = url
        }
        Unit
    }

    override fun deleteApi(id: Int) = transaction(db) {
        Apis.deleteWhere { Apis.id eq id }
        Unit
    }

    override fun getApi(id: Int): Api? = transaction(db) {
        Api.findById(id)
    }

    fun getApiByUrl(url: String): Api? = transaction(db) {
        Api.find { Apis.url eq url }.firstOrNull()
    }

    override fun getApiResponse(id: Int): ApiResponse? = transaction(db) {
        ApiResponse.findById(id)
    }

    override fun getAllApis(): List<Api> = transaction(db) {
        Api.all().toList()
    }

    override fun getAllApiResponses(): List<ApiResponse> = transaction(db) {
        ApiResponse.all().toList()
    }

    override fun close() { }
}