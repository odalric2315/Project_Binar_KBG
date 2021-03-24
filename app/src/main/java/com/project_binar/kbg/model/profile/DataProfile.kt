package com.project_binar.kbg.model.profile


import com.google.gson.annotations.SerializedName

data class DataProfile(
    @SerializedName("email")
    val email: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("photo")
    val photo: String?
)