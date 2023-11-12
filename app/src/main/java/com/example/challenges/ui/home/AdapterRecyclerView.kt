package com.example.challenges.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.challenges.R
import com.example.challenges.data.MenuMakanan

class AdapterRecyclerView(private val data: ArrayList<MenuMakanan>) : RecyclerView.Adapter<AdapterRecyclerView.HomeViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterRecyclerView.HomeViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_home, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val (nama, harga, photo) = data[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvName.text = nama
        holder.tvHarga.text = harga
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val imgPhoto: ImageView = itemView.findViewById(R.id.foto_makanan)
        val tvName: TextView = itemView.findViewById(R.id.nama_makanan)
        val tvHarga: TextView = itemView.findViewById(R.id.harga_makanan)
    }
}