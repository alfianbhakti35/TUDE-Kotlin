package com.efix.tude.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.efix.tude.R
import com.efix.tude.adapter.GuideItem
import com.efix.tude.model.Guide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_admin_menu.*
import kotlinx.android.synthetic.main.fragment_home_user.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeUserFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fathGuideList()

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
        return inflater.inflate(R.layout.fragment_home_user, container, false)
    }

    companion object {

        val GUDE_KEY = "GUDE_KEY"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeUserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun fathGuideList(){
        val ref = FirebaseDatabase.getInstance().getReference("/guide")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                snapshot.children.forEach {
                    val guide = it.getValue(Guide::class.java)
                    if (guide != null){
                        adapter.add(GuideItem(guide))
                    }
                }
                
                adapter.setOnItemClickListener { item, view ->
                    val guideItem = item as GuideItem

                    val intent = Intent(view.context, InputTransaksiActivity::class.java)
                    intent.putExtra(GUDE_KEY, guideItem.guide)
                    startActivity(intent)
                }
                
                recyclerVewListGuideUser.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}