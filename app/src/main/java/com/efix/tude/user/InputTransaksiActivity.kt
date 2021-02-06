package com.efix.tude.user

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.efix.tude.R
import com.efix.tude.adapter.KomentarItem
import com.efix.tude.model.Guide
import com.efix.tude.model.Komentar
import com.efix.tude.model.Transaksi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_input_transaksi.*
import java.util.*

class InputTransaksiActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_transaksi)
        val guide = intent.getParcelableExtra<Guide>(HomeUserFragment.GUDE_KEY)
        supportActionBar?.title = guide?.destinasi
//================================================================================================

        var sbNamaUser = StringBuilder()
        var sbNoHp = StringBuilder()
        var sbImg = StringBuilder()

        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val nama = snapshot.child("nama").getValue()
                val noHp = snapshot.child("noHp").getValue()
                val img = snapshot.child("img").getValue()

                sbNamaUser.append(nama)
                sbNoHp.append(noHp)
                sbImg.append(img)

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TAGS","Data = ${error.message}")
            }
        })

//===================================KOMENTAR======================================================
        val idGuide = guide?.id.toString()

        Log.d("TAGS", idGuide)
        val getKomentar = FirebaseDatabase.getInstance().getReference("/komentar")
        getKomentar.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                snapshot.children.forEach {

                    val komentar = it.getValue(Komentar::class.java)

                    if (komentar != null && komentar.idGuide == idGuide){
                            adapter.add(KomentarItem(komentar))
                    }
                }
                recyclerViewKomentar111.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TAGS", error.message)
            }
        })

//==================================================================================================

        Picasso.get()
                .load(guide?.img)
                .fit()
                .centerCrop()
                .into(imgInputTransaksiUser)
        tvNamaDestinasiInputTransaksi.setText(guide?.destinasi)
        tvNamaGuideInputTransaksi.setText(guide?.guide)
        tvNamaHargaInputTransaksi.setText(guide?.harga)

//        Calender
        val c = Calendar.getInstance()
        val tahun = c.get(Calendar.YEAR)
        val bulan = c.get(Calendar.MONTH)
        val tanggal = c.get(Calendar.DAY_OF_MONTH)

        btnPicTgl.setOnClickListener {
            val pic = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                var mb = month + 1
                editTextDateTransaksiUser.setText("" + dayOfMonth + "/" + mb + "/" + year)
            }, tahun, bulan, tanggal)

            pic.show()
        }




        btnBookNow.setOnClickListener {
            if(editTextDateTransaksiUser.text.toString().isEmpty()){
                Toast.makeText(this, "Tanggal Harus diisi !", Toast.LENGTH_SHORT).show()
            }else{
                val fileName = UUID.randomUUID().toString()
                val uid = FirebaseAuth.getInstance().uid
                val ref = FirebaseDatabase.getInstance().getReference("/transaksi/$fileName")

                var nama = sbNamaUser.toString()
                var noHpIn = sbNoHp.toString()
                var destinasi = guide?.destinasi.toString()
                var guideIn = guide?.guide.toString()
                var harga = guide?.harga.toString()
                var tgl = editTextDateTransaksiUser.text.toString()

                val status = "sukses"
                val transaksi = Transaksi(fileName, uid.toString(), nama, destinasi, guideIn, noHpIn, harga, tgl, status, idGuide, sbImg.toString())

                ref.setValue(transaksi)
                        .addOnSuccessListener {
                            val intent = Intent(this, UserMenuActivity::class.java)
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            Toast.makeText(this, "Transaksi Behasil", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Transaksi Gagal ${it.message}", Toast.LENGTH_SHORT).show()
                        }
            }
            }

    }



}