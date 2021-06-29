package com.example.studentverificationapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.studentverificationapp.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class AdminDashBoardActivity : AppCompatActivity() {
    private lateinit var gotoAddStudent:ExtendedFloatingActionButton
    private lateinit var gotoAddSecurityOfficer:ExtendedFloatingActionButton
    private lateinit var gotoSecurityChat:ExtendedFloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dash_board)
        gotoAddStudent = findViewById(R.id.add_student)
        gotoAddSecurityOfficer= findViewById(R.id.add_security_officer)
        gotoSecurityChat=findViewById(R.id.security_chat)

        gotoSecurityChat.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@AdminDashBoardActivity,SecurityChat::class.java))
        })
        gotoAddSecurityOfficer.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@AdminDashBoardActivity,StudentListActivity::class.java))
        })
        gotoAddStudent.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@AdminDashBoardActivity,AddStudent::class.java))
        })
    }
}