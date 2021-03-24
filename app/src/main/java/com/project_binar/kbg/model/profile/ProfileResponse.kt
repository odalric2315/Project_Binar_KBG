package com.project_binar.kbg.model.profile


import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("data")
    val `data`: DataProfile?,
    @SerializedName("success")
    val success: Boolean?
)