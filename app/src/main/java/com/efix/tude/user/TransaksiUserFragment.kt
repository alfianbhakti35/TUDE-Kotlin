package com.efix.tude.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.efix.tude.R
import com.efix.tude.adapter.TransaksiItem
import com.efix.tude.model.Transaksi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_transaksi_user.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TransaksiUserFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        fathTransaksiUser()

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaksi_user, container, false)
    }

    companion object {
        val TRANS_KEY = "TRANS_KEY"


        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TransaksiUserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun fathTransaksiUser(){
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("transaksi/")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()
                snapshot.children.forEach {
                    val transaksi = it.getValue(Transaksi::class.java)
                    if (transaksi != null){
                        if (transaksi.uid == uid){
                            adapter.add(TransaksiItem(transaksi))
                        }
                    }
                }
                adapter.setOnItemClickListener { item, view ->
                    val transaksiItem = item as TransaksiItem

                    val intent = Intent(view.context, StatusTransaksiActivity::class.java)
                    intent.putExtra(TRANS_KEY, transaksiItem.trasaksi)
                    startActivity(intent)
                }
                recyclerVewListTransaksiUser.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}