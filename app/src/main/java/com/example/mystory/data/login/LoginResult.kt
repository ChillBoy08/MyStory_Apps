package com.example.mystory.data.login

import com.google.gson.annotations.SerializedName

data class LoginResult(

    @field:SerializedName("token")
    val token: String

)