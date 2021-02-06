package com.efix.tude.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.efix.tude.R
import com.efix.tude.model.Guide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add_guide.*
import java.util.*

class AddGuideActivity : AppCompatActivity() {

    var selecPhoto : Uri? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_guide)
        supportActionBar?.title = "Add Destinasi & Guide"

        btnPilihPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        btnSimpanGuide.setOnClickListener {
            progressAddGuide.visibility = View.VISIBLE
            uploadData()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            selecPhoto = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selecPhoto)
            ivDestinasi.setImageBitmap(bitmap)
        }
    }

    private fun uploadData(){
        val fileName = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("images/$fileName")


        ref.putFile(selecPhoto!!)
                .addOnSuccessListener {
                    Log.d("TAGS", "Sukses upload = ${it.metadata?.path}")

                    ref.downloadUrl
                            .addOnSuccessListener {
                                saveToFirebaseDatabase(it.toString(), fileName)
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                            }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
    }

    private fun saveToFirebaseDatabase(imageUrl : String, nameFile : String){
        val ref = FirebaseDatabase.getInstance().getReference("guide/$nameFile")

        val guide = Guide(nameFile, etNamaDestinasi.text.toString(), etNamaGuide.text.toString(), etHarga.text.toString(), imageUrl)

        Log.d("TAGS", "DATA = ${guide.destinasi}")

        ref.setValue(guide)
                .addOnSuccessListener {

                    Toast.makeText(this, "Data Berhasil disimpan", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, AdminMenuActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    progressAddGuide.visibility = View.GONE
                    startActivity(intent)
                }
                .addOnFailureListener {
                    progressAddGuide.visibility = View.GONE
                    Toast.makeText(this, "Data Gagal disimpan", Toast.LENGTH_SHORT).show()
                }
    }

}