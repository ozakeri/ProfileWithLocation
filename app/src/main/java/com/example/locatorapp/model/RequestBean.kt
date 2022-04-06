package com.example.locatorapp.model

import android.os.Parcel
import android.os.Parcelable

data class RequestBean(
    var region: Int,
    var address: String?,
    var coordinate_mobile: String?,
    var coordinate_phone_number: String?,
    var first_name: String?,
    var gender: String?,
    var last_name: String?,
    var lat: Double,
    var lng: Double
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(region)
        parcel.writeString(address)
        parcel.writeString(coordinate_mobile)
        parcel.writeString(coordinate_phone_number)
        parcel.writeString(first_name)
        parcel.writeString(gender)
        parcel.writeString(last_name)
        parcel.writeDouble(lat)
        parcel.writeDouble(lng)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RequestBean> {
        override fun createFromParcel(parcel: Parcel): RequestBean {
            return RequestBean(parcel)
        }

        override fun newArray(size: Int): Array<RequestBean?> {
            return arrayOfNulls(size)
        }
    }
}