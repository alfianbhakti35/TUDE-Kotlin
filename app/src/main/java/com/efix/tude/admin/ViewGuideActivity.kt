package com.efix.tude.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.efix.tude.R
import com.efix.tude.model.Guide
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view_guide.*

class ViewGuideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_guide)

        val gude = intent.getParcelableExtra<Guide>(AdminMenuActivity.GUDE_ADMIN_KEY)

        supportActionBar?.title = gude?.destinasi

        tvViewGuideDestinasi.setText(gude?.destinasi.toString())
        tvViewGuideGuide.setText(gude?.guide.toString())
        tvViewGuideHarga.setText(gude?.harga.toString())

        Picasso.get()
            .load(gude?.img)
            .fit()
            .centerCrop()
            .into(imgViewGuide)

        btnViewGuideUpdae.setOnClickListener {
            val destinasi = tvViewGuideDestinasi.text.toString()
            val namaGude = tvViewGuideGuide.text.toString()
            val harga = tvViewGuideHarga.text.toString()

            val ref = FirebaseDatabase.getInstance().getReference("/guide/${gude?.id}")
            val updateGuide = Guide(gude?.id.toString(), destinasi, namaGude, harga, gude?.img.toString())

            ref.setValue(updateGuide)
                    .addOnSuccessListener {
                        val intent = Intent(this, AdminMenuActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)

                        Toast.makeText(this, "Data Berhasil diUpdate!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Log.d("TAGS", "GAGAL UPDATE DATA")
                    }
        }

        btnViewGuideHapus.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("/guide/")
            ref.child(gude?.id.toString()).removeValue()
                    .addOnSuccessListener {
                        val intent = Intent(this, AdminMenuActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)

                        Toast.makeText(this, "Data Berhasil diHpaus!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "GAGAL HAPUS!", Toast.LENGTH_SHORT).show()
                    }
        }

    }
}