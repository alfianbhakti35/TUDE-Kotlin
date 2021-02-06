package com.efix.tude.adapter

import com.efix.tude.R
import com.efix.tude.model.Guide
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.guide_row.view.*

class GuideItem(val guide: Guide):Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.tvDestinasiRaw.text = guide.destinasi
        viewHolder.itemView.tvHargaRow.text = guide.harga

        Picasso.get()
            .load(guide.img)
            .fit()
            .centerCrop()
            .into(viewHolder.itemView.imgGuideRaw)

    }
    override fun getLayout(): Int {
        return R.layout.guide_row
    }
}