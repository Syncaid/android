package com.example.syncaid.view.patient

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.syncaid.R
import com.example.syncaid.model.Faint
import com.example.syncaid.viewmodel.faintViewModel
import com.google.android.material.snackbar.Snackbar


class faintAdapter(private var mList: MutableList<Faint>) : RecyclerView.Adapter<faintAdapter.ViewHolder>() {
    val FaintViewModel = faintViewModel()

        // create new views
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // inflates the card_view_design view
            // that is used to hold list item
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.faint_card, parent, false)

            return ViewHolder(view)
        }

        // binds the list items to a view
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val faint = mList[position]


            holder.duration.text = faint.Duration
            holder.date.text = faint.Time

            holder.button.setOnClickListener {


                FaintViewModel.deleteFaint(faint._id) { response, code ->
                    if (code == 200) {
                        Log.d("SUCCESS", "Response: $response")
                        val index = mList.indexOf(faint)
                        mList.removeAt(index)
                        notifyItemRemoved(index)

                    } else {
                        Log.e("ERROR", "Error: API call failed")
                    }


            }

        }}

        // return the number of the items in the list
        override fun getItemCount(): Int {
            return mList.size
        }

        // Holds the views for adding it to image and text
        class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
            val duration: TextView = itemView.findViewById(R.id.Duration)
            val date: TextView = itemView.findViewById(R.id.Date)
            val button: ImageButton = itemView.findViewById(R.id.Delete)
        }


}