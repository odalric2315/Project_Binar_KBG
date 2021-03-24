package com.project_binar.kbg.model.login


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    val data: DataLogin?,
    @SerializedName("success")
    val success: Boolean?
)