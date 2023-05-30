package com.example.hotelreservationsystem.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelreservationsystem.Models.HotelResponse
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.TestModels.DataModel
import com.example.hotelreservationsystem.utils.NetworkResult

class RecomenderAdapter(val context:Context, val data: List<DataModel>): RecyclerView.Adapter<RecomenderAdapter.MyViewHolder>() {

    class MyViewHolder (itemview: View) : RecyclerView.ViewHolder(itemview)
    {
        val name = itemview.findViewById<TextView>(R.id.hotel_name_recommender)
        val location = itemview.findViewById<TextView>(R.id.hotel__recommender_loaction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_for_recommender,parent,false)
        return  MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = data.get(position).name
        holder.location.text = data.get(position).location
    }

}
