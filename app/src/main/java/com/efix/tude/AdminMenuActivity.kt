package com.efix.tude

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.efix.tude.adapter.GuideItem
import com.efix.tude.admin.AddGuideActivity
import com.efix.tude.admin.HomeFragment
import com.efix.tude.admin.ProfileFragment
import com.efix.tude.admin.TransaksiFragment
import com.efix.tude.model.Guide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_admin_menu.*

class AdminMenuActivity : AppCompatActivity() {

    var isOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_menu)

//        Load Animation
        val fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        val rotetAntiwise = AnimationUtils.loadAnimation(this, R.anim.rotet_antiwise)
        val rotetClockwise = AnimationUtils.loadAnimation(this, R.anim.rotet_clockwise)

        floatingAddGuideActionButton.startAnimation(fabClose)
        floatingTransaksiActionButton.startAnimation(fabClose)

        floatingActionButton.setOnClickListener {
            if (isOpen){
                floatingAddGuideActionButton.startAnimation(fabClose)
                floatingTransaksiActionButton.startAnimation(fabClose)
                floatingActionButton.startAnimation(rotetClockwise)

                isOpen = false
            }else{
                floatingAddGuideActionButton.startAnimation(fabOpen)
                floatingTransaksiActionButton.startAnimation(fabOpen)
                floatingActionButton.startAnimation(rotetAntiwise)

                floatingAddGuideActionButton.isClickable
                floatingTransaksiActionButton.isClickable

                isOpen = true
            }
        }

        floatingAddGuideActionButton.setOnClickListener {
            val intent = Intent(this, AddGuideActivity::class.java)
            startActivity(intent)
        }
        fathGuide()


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addGuide -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fathGuide(){
        val ref = FirebaseDatabase.getInstance().getReference("/guide")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                snapshot.children.forEach {
                    val guide = it.getValue(Guide::class.java)
                    if (guide != null){
                        adapter.add(GuideItem(guide))
                    }
                }
                recyclerViewGuide.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }


}