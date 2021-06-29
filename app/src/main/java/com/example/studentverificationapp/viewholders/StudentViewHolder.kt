package com.example.studentverificationapp.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentverificationapp.R

class StudentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    var studname:TextView
    var studImage:ImageView
    var studReg:TextView
    var studeDelete:ImageView


    init {
        studImage = itemView.findViewById(R.id.stud_image)
        studname =  itemView.findViewById(R.id.stud_name)
        studReg =  itemView.findViewById(R.id.stud_regno)
        studeDelete = itemView.findViewById(R.id.ic_delete_stud)

    }
}