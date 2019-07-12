package com.komo.model

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("_id") val id: String,
                    val username: String,
                    val password: String,
                    val name: String,
                    val userToken: UserToken,
                    val userApiState: List<UserApiState>)
                            