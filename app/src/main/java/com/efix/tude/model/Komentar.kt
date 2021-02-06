package com.efix.tude.model

class Komentar(val id : String, val uid : String, val komentars : String, val reting : Float, val idGuide : String, val nama : String, val img : String){
    constructor():this("", "", "", 0f, "", "", String())
}