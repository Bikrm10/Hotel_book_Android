package com.example.hotelreservationsystem.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelreservationsystem.Models.Booking
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.ViewModels.GetAllHotelViewModel
import com.example.hotelreservationsystem.ViewModels.HotelViewModel
import com.example.hotelreservationsystem.utils.constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Text
class BookingsAdapter(val context:Context,val bookingData : List<Booking>,private val getAllHotelViewModel: GetAllHotelViewModel):RecyclerView.Adapter<BookingsAdapter.MyViewHolder>()
{


    inner class MyViewHolder (itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val hotel_name = itemView.findViewById<TextView>(R.id.user_book_hotel_name)
        val room_number = itemView.findViewById<TextView>(R.id.user_book_room_no)
        val checkinDate = itemView.findViewById<TextView>(R.id.user_book_check_in_date)
        val checkoutDate = itemView.findViewById<TextView>(R.id.user_book_check_out_date)
        val cancelbooking = itemView.findViewById<TextView>(R.id.user_cancel_booking)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.user_bookings_view_layout,parent,false)
        return  MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookingData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.hotel_name.text = bookingData.get(position).hotel.name.toString()
        holder.room_number.text = bookingData.get(position).room.number.toString()
        holder.checkinDate.text = bookingData.get(position).startDate.toString()
        holder.checkoutDate.text = bookingData.get(position).endDate.toString()
        holder.cancelbooking.setOnClickListener {

            val userId = bookingData.get(position).user._id
            val hotelId  = bookingData.get(position).hotel._id
            val roomId = bookingData.get(position).room._id
            val roomNo = bookingData.get(position).room.number
            val bookingId = bookingData.get(position)._id

            Log.d(TAG,"okay so datas are in userId $userId \n hotelId $hotelId \n roomId $roomId \n roomNumber $roomNo \n bookingId $bookingId  ")

            getAllHotelViewModel.cancelBooking(userId,hotelId,roomId,bookingId)

        }

    }


}

//class ReviewsAdapterTest(val context : Context, val data: List<ReviewModel>): RecyclerView.Adapter<ReviewsAdapterTest.MyViewHolder>() {
//
//   inner class MyViewHolder (itemview: View) : RecyclerView.ViewHolder(itemview)
//    {
//        val name = itemview.findViewById<TextView>(R.id.user_name)
//        val date= itemview.findViewById<TextView>(R.id.review_date)
//        val user_image =  itemview.findViewById<ShapeableImageView>(R.id.shapeableImageView)
//        val description =  itemview.findViewById<TextView>(R.id.review)
//        val rate =  itemview.findViewById<TextView>(R.id.rate_num)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val view = LayoutInflater.from(context).inflate(R.layout.reviews_layout_resources,parent,false)
//        return  MyViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return  data.size
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val lb =data[position];
//        holder.name.text = data.get(position).name.toString()
//        holder.date.text= data.get(position).date.toString()
//       holder.user_image.setImageResource(lb.profile_picture)
//        holder.rate.text = lb.rate.toString()
//        holder.description.text = lb.review.toString()
//
//
//
//    }
//
//}