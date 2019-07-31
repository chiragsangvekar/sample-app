package com.example.sampleapplication

import android.os.Parcel
import android.os.Parcelable

data class ServiceIntent (
    val inputString : String,
    val senderClass : String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(inputString)
        parcel.writeString(senderClass)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ServiceIntent> {
        override fun createFromParcel(parcel: Parcel): ServiceIntent {
            return ServiceIntent(parcel)
        }

        override fun newArray(size: Int): Array<ServiceIntent?> {
            return arrayOfNulls(size)
        }
    }
}
