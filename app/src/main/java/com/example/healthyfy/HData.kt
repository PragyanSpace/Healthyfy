package com.example.healthyfy

import android.os.Parcel
import android.os.Parcelable

data class HData(val hname:String?,val hloc:String?,val hnum:String?,val id:String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(hname)
        parcel.writeString(hloc)
        parcel.writeString(hnum)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HData> {
        override fun createFromParcel(parcel: Parcel): HData {
            return HData(parcel)
        }

        override fun newArray(size: Int): Array<HData?> {
            return arrayOfNulls(size)
        }
    }

}
