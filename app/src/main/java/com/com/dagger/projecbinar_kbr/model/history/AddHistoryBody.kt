package com.example.mygameapplication.model.addhistory


import com.google.gson.annotations.SerializedName

data class AddHistoryBody(
    @SerializedName("mode")
    val mode: String?,
    @SerializedName("result")
    val result: String?
)