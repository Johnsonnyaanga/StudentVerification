package com.example.studentverificationapp.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.studentverificationapp.R
import com.example.studentverificationapp.models.Student
import com.firebase.ui.auth.data.model.User
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class AddStudent : AppCompatActivity() {
    private lateinit var btnAddStudents: Button
    private lateinit var studentName: TextInputEditText
    private var mImageUri: Uri? = null
    private var mImageView: CircleImageView? = null
    var mStorageRef: StorageReference? = null
    //private lateinit var post:TextInputEditText
    private val mReference: DatabaseReference = FirebaseDatabase
        .getInstance().reference.child("studentverificationapp")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        mStorageRef = FirebaseStorage.getInstance().getReference("studentverificationapp")
        studentName = findViewById(R.id.contestant_name_input)
        mImageView = findViewById(R.id.contestant_image)
        mImageView?.setOnClickListener(View.OnClickListener { selectImage() })
        //post = view.findViewById(R.id.contestant_post_input)
        btnAddStudents = findViewById(R.id.add_contestatnts)

        btnAddStudents.setOnClickListener(View.OnClickListener {

            checkIfExists(studentName.text.toString())



        })


    }
    private fun addStudent(name: String, imageurl: String){

        val student = Student(name, imageurl)
        val stud = mReference.child("students")
        stud.child(stud.push().key!!).setValue(student)
            .addOnSuccessListener { success ->
                toasMessage("successiful upload")

            }

    }
    private fun toasMessage(message: String){
        Toast.makeText(this@AddStudent, message, Toast.LENGTH_SHORT).show()

    }
    private fun selectImage() {
        val gallery = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        )
        resultLauncher.launch(gallery)
    }

    private fun checkIfExists(regno: String)
    {

        val stud = mReference.child("students")
        stud.addListenerForSingleValueEvent(object : ValueEventListener {
            var existsList = arrayListOf<String>()




            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.e("initstud",existsList.size.toString())


                for (snapshot in dataSnapshot.children) {
                        val regnumber = snapshot.getValue(Student::class.java)
                         var nameval = regnumber?.name.toString()
                      existsList.clear()
                      if (nameval.equals(regno)){
                          existsList.add(nameval)
                      }
                    }

                    Log.e("studlist",existsList.size.toString())

                    if (existsList.size>0){
                        toasMessage("Student already Exists")
                        return
                    }else{
                        addStud(regno)
                        return
                    }


                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

    }
    private fun getFileExtension(uri: Uri?): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR?.getType(uri!!))
    }

    private fun addStud(stud:String){
        if (mImageUri != null) {

            val fileReference = mStorageRef!!
                .child("students_images")
                .child(
                    System.currentTimeMillis().toString() + "." + getFileExtension(mImageUri)
                )
            fileReference.putFile(mImageUri!!)
                .addOnSuccessListener {
                    fileReference.downloadUrl.addOnSuccessListener { uri ->
                        val imageuri: String? = uri.toString()

                        addStudent(
                            stud,
                            imageuri!!,
                        )
                        startActivity(
                            Intent(
                                this@AddStudent,
                                AdminDashBoardActivity::class.java
                            )
                        )

                    }

                }
                .addOnFailureListener { failure ->
                    Log.d("errormessage", failure.message.toString())
                }
        } else {
            toasMessage("Select Image Please")
        }
    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
        if (result.resultCode == Activity.RESULT_OK && result.data != null ){
            mImageUri = result.data!!.data
            Picasso.get().load(mImageUri).into(mImageView)

        }
    }
}