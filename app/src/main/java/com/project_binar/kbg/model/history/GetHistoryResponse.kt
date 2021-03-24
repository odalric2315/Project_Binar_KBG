package com.project_binar.kbg.model.history


import com.google.gson.annotations.SerializedName

data class GetHistoryResponse(
    @SerializedName("data")
    val historyData: List<GetHistoryData>?,
    @SerializedName("success")
    val success: Boolean?
)