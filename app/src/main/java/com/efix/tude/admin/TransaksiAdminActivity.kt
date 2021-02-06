package com.efix.tude.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.efix.tude.R
import com.efix.tude.adapter.TransaksiAdminItem
import com.efix.tude.model.Transaksi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_transaksi_admin.*

class TransaksiAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi_admin)

        supportActionBar?.title = "Daftar Transaksi"

        transaksiUser()

    }

    private fun transaksiUser(){
        val ref = FirebaseDatabase.getInstance().getReference("/transaksi/")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val adaptor = GroupAdapter<ViewHolder>()
                snapshot.children.forEach {
                    val transaksi = it.getValue(Transaksi::class.java)

                    if (transaksi != null){
                        adaptor.add(TransaksiAdminItem(transaksi))
                    }
                }
                recyclerViewTransaksiAdmin.adapter = adaptor
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TAGS", error.message)
            }
        })
    }
}