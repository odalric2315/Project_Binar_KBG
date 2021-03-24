package com.project_binar.kbg.model.history


import com.google.gson.annotations.SerializedName

data class AddHistoryResponse(
    @SerializedName("data")
    val addHistoryData: AddHistoryData?,
    @SerializedName("success")
    val success: Boolean?
)