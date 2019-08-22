package com.example.sampleapplication.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class ImageNwModel(@SerializedName("items") val pageMaps: ArrayList<Items>?)

@Parcelize
data class Items(@SerializedName("pagemap") val pageMaps: CseImage?,
                 @SerializedName("title") val title: String,
                 @SerializedName("snippet") val snippet: String) : Parcelable

@Parcelize
data class CseImage(@SerializedName("cse_image") val image: List<Src>?,
                    @SerializedName("cse_thumbnail") val thumbnail: List<Src>?) :Parcelable

@Parcelize
data class Src(@SerializedName("src") val link: String?) :Parcelable

@Parcelize
data class ImageUIModel(val pages: ArrayList<Items>?, val error: String) : Parcelable
