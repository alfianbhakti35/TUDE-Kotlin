package com.efix.tude.adapter

import com.efix.tude.R
import com.efix.tude.model.Transaksi
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.transaksi_admin_row.view.*

class TransaksiAdminItem(val transaksi: Transaksi):Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.namaTransaksiAdmin.text = transaksi.nama
        viewHolder.itemView.tvDestinasiTransaksiAdminRow.text = transaksi.destinasi
    }
    override fun getLayout(): Int {
        return R.layout.transaksi_admin_row
    }
}