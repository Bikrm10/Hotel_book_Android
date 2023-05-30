package com.example.hotelreservationsystem.Adapters

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelreservationsystem.Models.Booking
import com.example.hotelreservationsystem.Models.BookingX
import com.example.hotelreservationsystem.R

class BookingRoomAdapter(val context: Context, val bookedRoomList:List<Booking>):
    RecyclerView.Adapter<BookingRoomAdapter.MyViewHolder>() {


    class MyViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        val username  =itemView.findViewById<TextView>(R.id.user_id)
        val room_no  =itemView.findViewById<TextView>(R.id.room_no)
        val start_date  =itemView.findViewById<TextView>(R.id.check_in_date)
        val end_date  =itemView.findViewById<TextView>(R.id.check_out_date)
        val status  =itemView.findViewById<TextView>(R.id.room_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): BookingRoomAdapter.MyViewHolder {
         val view = LayoutInflater.from(context).inflate(R.layout.owners_room_booking_details,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingRoomAdapter.MyViewHolder, position: Int) {
        holder.start_date.text=bookedRoomList.get(position).startDate.substringBefore("T")

        holder.end_date.text=bookedRoomList.get(position).endDate.substringBefore("T")
        holder.username.text=bookedRoomList.get(position).user.username
        holder.room_no.text=bookedRoomList.get(position).room.number.toString()
        holder.status.text="booked"
//        holder.booking_id.text = bookedRoomList.get(position).

    }

    override fun getItemCount(): Int {
       return bookedRoomList.size
    }
}