package com.efix.tude.adapter

import com.efix.tude.R
import com.efix.tude.model.Transaksi
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.transaksi_row.view.*

class TransaksiItem(val trasaksi : Transaksi):Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tvTransaksiDestinasiRow.text = trasaksi.destinasi
        viewHolder.itemView.tvTransaksiTanggalRow.text = trasaksi.harga
        viewHolder.itemView.tvTransaksiHargaRow.text = trasaksi.tanggal
    }

    override fun getLayout(): Int {
        return R.layout.transaksi_row
    }
}