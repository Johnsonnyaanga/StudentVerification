package com.example.studentverificationapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.example.studentverificationapp.R
import com.example.studentverificationapp.models.SecurityOfficer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RegisterActivity : AppCompatActivity() {
    private val mReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("studentverificationapp")
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var nameEdt: TextInputEditText
    private lateinit var progressBar: ProgressBar
    private lateinit var SButton: ExtendedFloatingActionButton
    private lateinit var textLinkSignIn: LinearLayout
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progress)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        nameEdt =  findViewById(R.id.name_EDT)
        SButton = findViewById(R.id.sign_in)
        textLinkSignIn = findViewById(R.id.sign_in_text_link)
        textLinkSignIn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        })


        SButton.setOnClickListener(View.OnClickListener {
            if (checkInput(email.text.toString(),password.text.toString()))
            {
                registerSecurityOfficer(email.text.toString(),password.text.toString(),nameEdt.text.toString())
            }else{
                Toast.makeText(this,"input all fields", Toast.LENGTH_SHORT).show()
            }

        })
    }
    private fun registerSecurityOfficer(mail:String , password:String,name:String) {

        progressBar.visibility = View.VISIBLE

        mAuth?.createUserWithEmailAndPassword(mail,password)
            ?.addOnCompleteListener(this, OnCompleteListener {
                    task ->
                if (task.isSuccessful){
                    // startActivity(Intent(this, AdminDashboard::class.java))
                }else
                {
                    //Toast.makeText(this,"not registered", Toast.LENGTH_LONG).show()
                }

            })?.addOnSuccessListener(this, OnSuccessListener {
                    task->
                addSecurityOfficer(name,mail)
                progressBar.visibility= View.GONE
                Toast.makeText(this,"registered succesifully", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, LoginActivity::class.java))
            })


            ?.addOnFailureListener(this, OnFailureListener {
                    exception ->
                Toast.makeText(this,exception.toString(), Toast.LENGTH_LONG).show()
            })
    }
    private fun addSecurityOfficer(mname:String,email:String){
        val watchmanReference = mReference.child("securityofficer")
        var watchie = SecurityOfficer(mname,email)
        watchmanReference.child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(watchie)
    }

    private fun checkInput(maila: String,passworda: String):Boolean
    {
        if (maila.isEmpty()||passworda.isEmpty()){
            return false
        }else{
            return true
        }
    }
}