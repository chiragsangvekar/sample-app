package com.example.sampleapplication

import com.google.gson.annotations.SerializedName

data class ListDataUIModel (
    val text: String,
    val url: String
)

data class DataNwModel (
    @SerializedName("first_name") val text: String?,
    @SerializedName("last_name") val url: String?
)

data class ListDataNwModel(
    @SerializedName("data") val list: List<DataNwModel>?
)