package com.efix.tude.model

class Transaksi(val uid : String, val nama : String, val destinasi : String, val guide : String, val noHp : String, val harga : String, val tanggal : String) {
    constructor() : this("", "", "", "", "", "", "")
}