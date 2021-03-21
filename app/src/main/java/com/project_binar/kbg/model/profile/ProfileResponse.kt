package com.example.mvvm2application.model.profile


import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("data")
    val `data`: DataProfile?,
    @SerializedName("success")
    val success: Boolean?
)