package com.example.hotelreservationsystem.Fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.databinding.FragmentDetailedRoomBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class DetailedRoomFragment : Fragment() {
    lateinit var binding:FragmentDetailedRoomBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedRoomBinding.inflate(layoutInflater,container,false)

        binding.pick1.setOnClickListener {
            val c = Calendar.getInstance()
            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    binding.checkInDate.text =((monthOfYear + 1).toString() + "/" + dayOfMonth + "/" + year)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        binding.pick2.setOnClickListener {
            val c = Calendar.getInstance()
            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    binding.checkOutDate.text = ((monthOfYear + 1).toString() + "/" + dayOfMonth + "/" + year)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        binding.confirm.setOnClickListener{

            try {

                var mDate1 = binding.checkInDate.text.toString()
                val mDate2 = binding.checkOutDate.text.toString()

                val mDateFormat = SimpleDateFormat("MM/dd/yyyy")

                // Converting the dates
                // from string to date format
                val mDate11 = mDateFormat.parse(mDate1)
                val mDate22 = mDateFormat.parse(mDate2)

                // Finding the absolute difference between
                // the two dates.time (in milli seconds)
                val mDifference = kotlin.math.abs(mDate11.time - mDate22.time)

                // Converting milli seconds to dates
                val mDifferenceDates = mDifference / (24 * 60 * 60 * 1000)

                // Converting the above integer to string
                val mDayDifference = mDifferenceDates.toString()

                Toast.makeText(requireContext(), "date Difference $mDayDifference", Toast.LENGTH_SHORT).show()
                binding.daysInterval.text= mDayDifference
            }
            catch (e:Exception)
            {
                Toast.makeText(requireContext(), "Data failed", Toast.LENGTH_SHORT).show()

            }
            if (binding.payCard.visibility==View.GONE)
            {

                binding.payCard.visibility=View.VISIBLE

            }
            else
            {

                binding.payCard.visibility=View.GONE

            }

        }










        return binding.root
    }


}