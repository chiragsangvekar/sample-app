package com.example.sampleapplication

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ImageListEntry (
    @SerializedName("imageUrl") val imageUrl : String,
    @SerializedName("imageName") val imageName : String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imageUrl)
        parcel.writeString(imageName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageListEntry> {
        override fun createFromParcel(parcel: Parcel): ImageListEntry {
            return ImageListEntry(parcel)
        }

        override fun newArray(size: Int): Array<ImageListEntry?> {
            return arrayOfNulls(size)
        }
    }
}