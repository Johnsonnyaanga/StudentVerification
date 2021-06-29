package com.example.studentverificationapp.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentverificationapp.R
import com.example.studentverificationapp.models.Messages
import com.example.studentverificationapp.viewholders.MessagesViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class SecurityChat : AppCompatActivity() {
    private lateinit var sendMsBtn: Button
    private lateinit var messageEditText:EditText
    private val mReference: DatabaseReference = FirebaseDatabase
        .getInstance().reference.child("studentverificationapp")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security_chat)
        val user = FirebaseAuth.getInstance().currentUser?.uid


        sendMsBtn = findViewById(R.id.send_message_btn)
        messageEditText= findViewById(R.id.messageEditText)
        sendMsBtn.setOnClickListener(View.OnClickListener {
            val name = getSenderName(user.toString())
            val currTime = getcurrentTime()
            sendMessage(name, currTime, messageEditText.text.toString())
        })









        val databaseReference = mReference.child("securitychat")
        val recyclerView: RecyclerView
        val options: FirebaseRecyclerOptions<Messages>
        val adapter: FirebaseRecyclerAdapter<Messages, MessagesViewHolder>
        databaseReference.keepSynced(true)
        databaseReference.keepSynced(true)
        recyclerView = findViewById(R.id.recycler_chat)
        recyclerView.layoutManager = LinearLayoutManager(this@SecurityChat)
        recyclerView.setHasFixedSize(true)
        options = FirebaseRecyclerOptions.Builder<Messages>()
            .setQuery(databaseReference, Messages::class.java).build()
        adapter = object : FirebaseRecyclerAdapter<Messages, MessagesViewHolder>(options) {
            override fun onBindViewHolder(holder: MessagesViewHolder, position: Int, model: Messages) {
                holder.name.setText(model.sender)
                holder.time.setText(model.time)
                holder.textMessage.setText(model.textMessage)

            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.messo_view, parent, false)
                return MessagesViewHolder(v)
            }
        }
        adapter.startListening()
        recyclerView.adapter = adapter



    }

    private fun sendMessage(sender: String, time: String, textMessage: String){
        val chat = mReference.child("securitychat")
        val message = com.example.studentverificationapp.models.Messages(sender, time, textMessage)
        chat.child(chat.push().key!!).setValue(message)
    }
    private fun getSenderName(userid: String):String{
        return "Admin Officer"
    }
    private fun getcurrentTime():String{
        val sai = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
        return sai
    }
}