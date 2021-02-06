package com.efix.tude.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Guide(val id : String, val destinasi : String, val guide : String, val harga : String, val img : String) : Parcelable{
    constructor() : this("", "", "", "", "")
}