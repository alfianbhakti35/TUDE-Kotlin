package com.efix.tude.adapter

import com.efix.tude.R
import com.efix.tude.model.Komentar
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.komentar_user_row.view.*

class KomentarItem (val komentar: Komentar):Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tvKomntarUserKomentarRow.text = komentar.komentars
        viewHolder.itemView.tvNamaUserKomentarRow.text = komentar.nama
        viewHolder.itemView.retingKomentarRow.numStars = komentar.reting.toInt()
        Picasso.get()
            .load(komentar.img)
            .fit()
            .centerCrop()
            .into(viewHolder.itemView.imgUserKomentarRow11)

    }

    override fun getLayout(): Int {
        return R.layout.komentar_user_row
    }
}