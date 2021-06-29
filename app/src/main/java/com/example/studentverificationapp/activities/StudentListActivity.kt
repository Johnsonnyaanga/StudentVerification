package com.example.studentverificationapp.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentverificationapp.R
import com.example.studentverificationapp.models.Messages
import com.example.studentverificationapp.models.Student
import com.example.studentverificationapp.viewholders.MessagesViewHolder
import com.example.studentverificationapp.viewholders.StudentViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class StudentListActivity : AppCompatActivity() {
    private val mReference: DatabaseReference = FirebaseDatabase
            .getInstance().reference.child("studentverificationapp")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_list_acitivity)

        val databaseReference = mReference.child("students")
        val recyclerView: RecyclerView
        val options: FirebaseRecyclerOptions<Student>
        val adapter: FirebaseRecyclerAdapter<Student, StudentViewHolder>
        databaseReference.keepSynced(true)
        databaseReference.keepSynced(true)
        recyclerView = findViewById(R.id.recycler_students_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        options = FirebaseRecyclerOptions.Builder<Student>()
                .setQuery(databaseReference, Student::class.java).build()
        adapter = object : FirebaseRecyclerAdapter<Student, StudentViewHolder>(options) {
            override fun onBindViewHolder(holder: StudentViewHolder, position: Int, model: Student) {
                holder.studname.setText(model.name)
                Picasso.get().load(model.imageUrl).into(holder.studImage)
                holder.studeDelete.setOnClickListener(View.OnClickListener {
                    //delete student
                    getRef(position).removeValue()
                })



            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
                val v = LayoutInflater.from(parent.context)
                        .inflate(R.layout.student_details_view, parent, false)
                return StudentViewHolder(v)
            }
        }
        adapter.startListening()
        recyclerView.adapter = adapter



    }
}