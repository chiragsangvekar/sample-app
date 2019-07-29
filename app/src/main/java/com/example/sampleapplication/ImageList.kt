package com.example.sampleapplication

import com.google.gson.annotations.SerializedName

data class ImageListEntry (
    @SerializedName("imageUrl") val imageUrl : String,
    @SerializedName("imageName") val imageName : String
)