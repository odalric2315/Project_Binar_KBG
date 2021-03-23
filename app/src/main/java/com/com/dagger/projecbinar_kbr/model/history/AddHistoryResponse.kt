package com.example.mygameapplication.model.addhistory


import com.google.gson.annotations.SerializedName

data class AddHistoryResponse(
    @SerializedName("data")
    val addHistoryData: AddHistoryData?,
    @SerializedName("success")
    val success: Boolean?
)