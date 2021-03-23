package com.example.mygameapplication.model.gethistory


import com.google.gson.annotations.SerializedName

data class GetHistoryResponse(
    @SerializedName("data")
    val historyData: List<GetHistoryData>?,
    @SerializedName("success")
    val success: Boolean?
)