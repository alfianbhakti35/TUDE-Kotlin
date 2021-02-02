package com.efix.tude.user

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.efix.tude.R
import com.efix.tude.model.Guide
import com.efix.tude.model.Transaksi
import com.efix.tude.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_input_transaksi.*
import kotlinx.android.synthetic.main.fragment_profile_user.*
import java.time.Year
import java.util.*

class InputTransaksiActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_transaksi)
        val guide = intent.getParcelableExtra<Guide>(HomeUserFragment.GUDE_KEY)
        supportActionBar?.title = guide?.destinasi

        Picasso.get().load(guide?.img).into(imgInputTransaksiUser)
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

        var sbNamaUser = StringBuilder()
        var sbNoHp = StringBuilder()

        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val nama = snapshot.child("nama").getValue()
                val noHp = snapshot.child("noHp").getValue()

                sbNamaUser.append(nama)
                sbNoHp.append(noHp)

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TAGS","Data = ${error.message}")
            }
        })


        btnBookNow.setOnClickListener {
            val fileName = UUID.randomUUID().toString()
            val uid = FirebaseAuth.getInstance().uid
            val ref = FirebaseDatabase.getInstance().getReference("/transaksi/$uid/$fileName")

            var nama = sbNamaUser.toString()
            var noHpIn = sbNoHp.toString()
            var destinasi = guide?.destinasi.toString()
            var guideIn = guide?.guide.toString()
            var harga = guide?.harga.toString()
            var tgl = editTextDateTransaksiUser.text.toString()

            val transaksi = Transaksi(uid.toString(), nama, destinasi, guideIn, noHpIn, harga, tgl)
            
            ref.setValue(transaksi)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Transaksi Behasil", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Transaksi Gagal ${it.message}", Toast.LENGTH_SHORT).show()
                    }
        }
    }


}