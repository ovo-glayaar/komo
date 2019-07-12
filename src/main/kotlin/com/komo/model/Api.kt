package com.komo.model

import com.google.gson.annotations.SerializedName

data class Api(@SerializedName("_id") val id: String,
               val name: String,
               var responses: List<ApiResponse>)
