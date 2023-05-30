package com.example.hotelreservationsystem.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotelreservationsystem.Models.Room
import com.example.hotelreservationsystem.R
import com.google.android.material.imageview.ShapeableImageView

class AvailableRoomsAdapter(val context : Context,  val Rooms:List<Room>): RecyclerView.Adapter<AvailableRoomsAdapter.MyViewHolder>()  {
    lateinit var  mlistner :onItemClickListner
    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner)
    {
        mlistner = listner
    }
    class MyViewHolder( itemView: View,listner: onItemClickListner): RecyclerView.ViewHolder(itemView) {
        val room_number = itemView.findViewById<TextView>(R.id.room_view_number)
        val room_view_type = itemView.findViewById<TextView>(R.id.room_view_type)
        val room_view_price = itemView.findViewById<TextView>(R.id.room_view_price)
        val room_view_image=itemView.findViewById<ShapeableImageView>(R.id.roomViewImage)
        val status = itemView.findViewById<TextView>(R.id.status)
        val bookNowTextView = itemView.findViewById<TextView>(R.id.book_now)
        init {
            itemView.setOnClickListener{
                listner.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_room_view_layout,parent,false)
        return MyViewHolder(view,mlistner)
    }

    override fun getItemCount(): Int {
      return Rooms.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.room_number.text = Rooms.get(position).number.toString()
        holder.room_view_type.text = Rooms.get(position).type.toString()

        if(Rooms.get(position).status.toString() == "true")
        {
            holder.status.text = "Available"
            holder.bookNowTextView.visibility = View.VISIBLE
        }
        else{
            holder.status.text  = "Booked"
            holder.bookNowTextView.visibility = View.INVISIBLE
        }
        holder.room_view_price.text = Rooms.get(position).price.toString()

        val string = Rooms.get(position).img.toString()
        Glide.with(this.context).load(string).into(holder.room_view_image)

        }
    }
