package com.example.sampleapplication

import com.google.gson.annotations.SerializedName

data class ListDataUIModel (
//    val text: String,
    val url: String
)

data class spellingDataModel(
    @SerializedName("correctedQuery") val correctedQuery : String,
    @SerializedName ("htmlCorrectedQuery") val htmlCorrectedQuery : String
)

data class ImageUrls(
    @SerializedName("src") val src:String
)

data class Pagemap(
    @SerializedName("cse_image") val cseImage : List<ImageUrls>
)

data class item(
    @SerializedName("pagemap") val pagemap : Pagemap
)

data class QueryResultModel(
    @SerializedName("kind") val kind:String,
    @SerializedName("spelling") val spelling :spellingDataModel,
    @SerializedName("items") val items:List<item>
//    @SerializedName
)