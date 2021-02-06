package com.efix.tude.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.efix.tude.R
import com.efix.tude.admin.AdminMenuActivity
import com.efix.tude.model.Komentar
import com.efix.tude.model.Transaksi
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_status_transaksi.*

class StatusTransaksiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_transaksi)

        val transaksi = intent.getParcelableExtra<Transaksi>(TransaksiUserFragment.TRANS_KEY)

//        Set Nilai
        tvStatusPesananUser.setText(transaksi?.status)
        tVNamaDestinasiStatusTransaksi.setText(transaksi?.destinasi)
        tVNamaGuideStatusTransaksi.setText(transaksi?.guide)
        tVHargaStatusTransaksi.setText(transaksi?.harga)
        tVTanggalStatusTransaksi.setText(transaksi?.tanggal)

        val imgV = "https://firebasestorage.googleapis.com/v0/b/tude-1870d.appspot.com/o/icon%2FtIME.png?alt=media&token=aeafa094-6a01-4952-b523-2c1daa2942ad"
        val imgS = "https://firebasestorage.googleapis.com/v0/b/tude-1870d.appspot.com/o/icon%2Fcheck.png?alt=media&token=5b74cf25-d275-426b-886f-fac3c46c8d98"

        //Nonaktifkan Tombol
        if (transaksi?.status == "verifikasi"){
            btnKomendanReting.visibility = View.GONE
            layoutKomentar.visibility = View.GONE
            Picasso.get().load(imgV).fit().centerCrop().into(imgStatusPesanan)
        }else{
            Picasso.get().load(imgS).fit().centerCrop().into(imgStatusPesanan)
        }

        btnKomendanReting.setOnClickListener {
            val id = transaksi?.id.toString()
            val idGuide = transaksi?.idGuide.toString()
            val uid = transaksi?.uid.toString()
            val ratingBar = rbRatingUser.rating
            val komentarInput = etKomentarUser.text.toString()

            val komentar = Komentar(id, uid, komentarInput, ratingBar, idGuide, transaksi?.nama.toString(), transaksi?.img.toString())

            val ref = FirebaseDatabase.getInstance().getReference("komentar/$id")
            ref.setValue(komentar)
                .addOnSuccessListener {
                    Toast.makeText(this, "Komentar Berhasil diTambahakan!", Toast.LENGTH_SHORT).show()
                    val ref = FirebaseDatabase.getInstance().getReference("/transaksi")
                    ref.child(transaksi?.id.toString()).removeValue()
                        .addOnSuccessListener {
                            val intent = Intent(this, UserMenuActivity::class.java)
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            Toast.makeText(this, "Transaksi Telah Selesai", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "GAGAL", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "GAGAL", Toast.LENGTH_SHORT).show()
                }


//            Toast.makeText(this, "CEK $ratingBar", Toast.LENGTH_SHORT).show()
        }




    }
}