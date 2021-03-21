package com.example.mvvm2application.model.register


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("data")
    val dataRegister: DataRegister?,
    @SerializedName("success")
    val success: Boolean?
)