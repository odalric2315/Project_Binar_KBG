package com.project_binar.kbg.model.register


import com.google.gson.annotations.SerializedName

data class RegisterBody(
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("username")
    val username: String?
)