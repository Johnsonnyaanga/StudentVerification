package com.example.studentverificationapp.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentverificationapp.R

class MessagesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    var name: TextView
    var time: TextView
    var textMessage:TextView

    init {
        name = itemView.findViewById(R.id.sender_name)
        textMessage =itemView.findViewById(R.id.text_message)
        time=itemView.findViewById(R.id.time)
    }
}