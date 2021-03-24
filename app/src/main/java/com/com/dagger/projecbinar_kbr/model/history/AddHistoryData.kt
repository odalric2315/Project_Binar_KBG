package com.example.mygameapplication.model.addhistory


import com.google.gson.annotations.SerializedName

data class AddHistoryData(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("mode")
    val mode: String?,
    @SerializedName("result")
    val result: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)