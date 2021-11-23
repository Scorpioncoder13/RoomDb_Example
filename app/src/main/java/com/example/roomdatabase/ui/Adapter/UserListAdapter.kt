package com.example.roomdatabase.ui.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.UserlistLayoutBinding
import com.example.roomdatabase.entity.Registration

class UserListAdapter(private val userlist: List<Registration>, val context: Context?) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        lateinit var binding: UserlistLayoutBinding
        fun bind(registration: Registration, context: Context?) {
            binding = UserlistLayoutBinding.bind(itemView)
            binding.txtFirstname.text = "FirstName : " + registration.firstname
            binding.txtLastname.text = "LastName : " + registration.lastname
            binding.txtAge.text = "Age : " + registration.age
            binding.txtEmail.text = "Email Id : " + registration.email
            binding.txtMobileno.text = "Mobile No : " + registration.mobileno

            binding.cardview.setOnClickListener {
                val navigator = it.findNavController()
                val bundle: Bundle = Bundle()
                bundle.putInt("id", registration.user_id)
                bundle.putString("isFrom", "btnpress")
                Navigation.findNavController(view = it)
                    .navigate(R.id.action_listing_to_register, bundle)


            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.userlist_layout, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var registration: Registration = userlist[position]
        holder.bind(registration, context)

    }

    override fun getItemCount(): Int {
        return userlist.size
    }
}