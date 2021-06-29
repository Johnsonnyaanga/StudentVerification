package com.example.studentverificationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.studentverificationapp.activities.LoginActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private val mReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("studentverificationapp")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //mReference.child("admins").child("dsL3ILv2CFXqI4hBrQE0G52Nisj1").setValue("dsL3ILv2CFXqI4hBrQE0G52Nisj1")
        Handler().postDelayed({
            val intent = Intent(this@MainActivity,LoginActivity::class.java)
            startActivity(intent)
        },2000)


    }
}