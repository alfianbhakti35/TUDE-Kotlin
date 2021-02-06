package com.efix.tude.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Transaksi(val id : String, val uid : String, val nama : String, val destinasi : String, val guide : String, val noHp : String, val harga : String, val tanggal : String, val status : String, val idGuide : String, val img : String) : Parcelable {
    constructor() : this("", "", "", "", "", "", "", "", "", "", "")
}