package com.example.hotelreservationsystem.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.TestModels.ReviewModel
import com.google.android.material.imageview.ShapeableImageView


class ReviewsAdapterTest(val context : Context, val data: List<ReviewModel>): RecyclerView.Adapter<ReviewsAdapterTest.MyViewHolder>() {

   inner class MyViewHolder (itemview: View) : RecyclerView.ViewHolder(itemview)
    {
        val name = itemview.findViewById<TextView>(R.id.user_name)
        val date= itemview.findViewById<TextView>(R.id.review_date)
        val user_image =  itemview.findViewById<ShapeableImageView>(R.id.shapeableImageView)
        val description =  itemview.findViewById<TextView>(R.id.review)
        val rate =  itemview.findViewById<TextView>(R.id.rate_num)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.reviews_layout_resources,parent,false)
        return  MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val lb =data[position];
        holder.name.text = data.get(position).name.toString()
        holder.date.text= data.get(position).date.toString()
       holder.user_image.setImageResource(lb.profile_picture)
        holder.rate.text = lb.rate.toString()
        holder.description.text = lb.review.toString()



    }

}

