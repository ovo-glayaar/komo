package com.komo

import com.komo.Apis
import com.komo.Users
import com.komo.model.Api
import com.komo.model.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DAOFacadeDatabase(val db: Database): DAOFacade {

    override fun init() = transaction(db) {
        SchemaUtils.create(Users)

        //Initial data
        /*
        val employees = listOf(Employee(1, "Owlette","owlette@techstack.net", "New York"),
                Employee(2, "Catboy","catboy@techstack.net", "New York"),
                Employee(3, "Grekko","grekko@techstack.net", "New York"))
        Employees.batchInsert(employees){ employee ->
            this[Employees.id] = employee.id
            this[Employees.name] = employee.name
            this[Employees.email] = employee.email
            this[Employees.city] = employee.city
        }
        */
        SchemaUtils.create(Apis)
    }

    //Users
    override fun createUser(user: User) = transaction(db) {
        Users.insert {it[Users.id] = user.id;
            it[Users.username] = user.username;
            it[Users.password] = user.password;
            it[Users.name] = user.name;
            it[Users.userToken] = user.userToken;
            it[Users.userApiState] = user.userApiState;
        }
        Unit
    }
    override fun updateUser(user: User) = transaction(db) {
        Users.update({Users.id eq user.id}){
            it[Users.username] = user.username;
            it[Users.password] = user.password;
            it[Users.name] = user.name;
            it[Users.userToken] = user.userToken;
            it[Users.userApiState] = user.userApiState;
        }
        Unit
    }

    override fun deleteUser(id: String) = transaction(db) {
        Users.deleteWhere { Users.id eq id }
        Unit
    }

    override fun getUser(id: String) = transaction(db) {
        Users.select { Users.id eq id }.map {
            User(it[Users.id], it[Users.username], it[Users.password],
                 it[Users.name], it[Users.userToken],
                 it[Users.userApiState])
        }.singleOrNull()
    }

    override fun getAllUsers() = transaction(db) {
        Users.selectAll().map {
            User(it[Users.id], it[Users.username], it[Users.password],
                    it[Users.name], it[Users.userToken],
                    it[Users.userApiState])
        }
    }

    //Apis
    override fun createApi(api: Api) = transaction(db) {
        Apis.insert {it[Apis.id] = api.id;
            it[Apis.name] = api.name;
            it[Apis.responses] = api.responses;
        }
        Unit
    }

    override fun updateApi(api: Api) = transaction(db) {
        Apis.update({Apis.id eq api.id}) {
            it[Apis.name] = api.name;
            it[Apis.responses] = api.responses;
        }
        Unit
    }

    override fun deleteApi(id: String) = transaction(db) {
        Apis.deleteWhere { Apis.id eq id }
        Unit
    }

    override fun getApi(id: String): Api? = transaction(db) {
        Apis.select { Apis.id eq id }.map {
            Api(it[Apis.id], it[Apis.name], it[Apis.responses])
        }.singleOrNull()
    }

    override fun getApiByCode(id: String, code: String): Api? = transaction(db) {
        var apiResult = Apis.select { Apis.id eq id }.map {
            Api(it[Apis.id], it[Apis.name], it[Apis.responses])
        }.singleOrNull()

        if(apiResult != null) {
            apiResult.responses = apiResult.responses.asSequence().filter { it.code == code }.toList()

            if(apiResult.responses.count() == 0) {
                apiResult = null
            }
        }

        apiResult
    }

    override fun getAllApis(): List<Api> = transaction(db) {
        Apis.selectAll().map {
            Api(it[Apis.id], it[Apis.name], it[Apis.responses])
        }
    }

    override fun close() { }
}