package com.example.syncaid.view.patient

import android.content.Context
import android.graphics.Outline
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.syncaid.R
import com.example.syncaid.model.Faint
import com.example.syncaid.model.User
import com.example.syncaid.viewmodel.faintViewModel
import com.example.syncaid.viewmodel.userViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject


class guardianAdapter(private val context: Context,private var mList: MutableList<User>) : RecyclerView.Adapter<guardianAdapter.ViewHolder>() {
    val UserViewModel = userViewModel()

        // create new views
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // inflates the card_view_design view
            // that is used to hold list item
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.guardian_card, parent, false)

            return ViewHolder(view)
        }

        // binds the list items to a view
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val user = mList[position]


            holder.name.text = user.FirstName+" "+user.LastName
            holder.email.text = user.Email
            Glide.with(context)
                .load(user.ProfilePhoto)
                .circleCrop()
                .into(holder.image)

            holder.button.setOnClickListener {

                val jsonObject = JsonObject().apply {
                    addProperty("id",user._id)
                }
                val sharedPrefs = context.getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
                val id = sharedPrefs?.getString("ID","null")

                if (id != null) {
                    UserViewModel.deleteGuardian(id,jsonObject) { response, code ->
                        if (code == 200) {
                            Log.d("SUCCESS", "Response: $response")
                            val index = mList.indexOf(user)
                            mList.removeAt(index)
                            notifyItemRemoved(index)

                        } else {
                            Log.e("ERROR", "Error: API call failed")
                        }


                    }
                }


        }}

        // return the number of the items in the list
        override fun getItemCount(): Int {
            return mList.size
        }

        // Holds the views for adding it to image and text
        class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
            val name: TextView = itemView.findViewById(R.id.Name)
            val email: TextView = itemView.findViewById(R.id.Email)
            val image: ImageView = itemView.findViewById(R.id.Image)
            val button: ImageButton = itemView.findViewById(R.id.Delete)
        }


}