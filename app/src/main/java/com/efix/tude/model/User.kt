package com.efix.tude.model

class User (val uid: String, val nama : String, val email : String, val noHp : String, val alamat : String, val password : String, val role : String, val img : String, val statusVerfikasiGuide : String, val levelGuide : String){
    constructor() : this("", "", "", "", "", "", "", "", "", "")
}

