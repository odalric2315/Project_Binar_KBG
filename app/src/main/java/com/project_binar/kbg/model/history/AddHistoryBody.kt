package com.project_binar.kbg.model.history


import com.google.gson.annotations.SerializedName

data class AddHistoryBody(
    @SerializedName("mode")
    val mode: String?,
    @SerializedName("result")
    val result: String?
)