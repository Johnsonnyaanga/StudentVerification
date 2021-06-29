package com.example.studentverificationapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentverificationapp.R
import com.example.studentverificationapp.models.Messages
import com.example.studentverificationapp.viewholders.MessagesViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class PublicChatFragment : Fragment() {
    private lateinit var sendMsBtn: Button
    private lateinit var messageEditText: EditText
    private val mReference: DatabaseReference = FirebaseDatabase
            .getInstance().reference.child("studentverificationapp")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_public_chat, container, false)

        val user = FirebaseAuth.getInstance().currentUser?.uid


        sendMsBtn = view.findViewById(R.id.send_message_btn)
        messageEditText= view.findViewById(R.id.messageEditText)
        sendMsBtn.setOnClickListener(View.OnClickListener {


            var sendername=""
            val mReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("studentverificationapp")
            val watchmanReference = mReference.child("securityofficer")


            watchmanReference.child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                watchmanReference.child(FirebaseAuth.getInstance().currentUser!!.uid).addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        sendername = snapshot.child("name").value.toString()
                                        val currTime = getcurrentTime()
                                        sendMessage(sendername, currTime, messageEditText.text.toString())
                                    }

                                    override fun onCancelled(error: DatabaseError) {}
                                })
                            } else {

                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                        }
                    })



        })









        val databaseReference = mReference.child("securitychat")
        val recyclerView: RecyclerView
        val options: FirebaseRecyclerOptions<Messages>
        val adapter: FirebaseRecyclerAdapter<Messages, MessagesViewHolder>
        databaseReference.keepSynced(true)
        databaseReference.keepSynced(true)
        recyclerView = view.findViewById(R.id.recycler_chat)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
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


        return view
    }
    private fun sendMessage(sender:String,time:String,textMessage:String){
        val chat = mReference.child("securitychat")
        val message = com.example.studentverificationapp.models.Messages(sender,time,textMessage)
        chat.child(chat.push().key!!).setValue(message)
    }
    private fun getSenderName(userid:String):String{
        return "John Doe"
    }
    private fun getcurrentTime():String{
        return "11.00 am"
    }

}