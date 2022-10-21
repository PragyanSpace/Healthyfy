package com.example.healthyfy

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.healthyfy.databinding.ActivityFillDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.util.*

class FillDetails : AppCompatActivity() {

    lateinit var binding:ActivityFillDetailsBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    lateinit var firestore: FirebaseFirestore
    lateinit var calendar: Calendar
    lateinit var listener: OnDateSetListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_fill_details)
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val intent = intent
        val category = intent.getStringExtra("Category")
        calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
//        binding.createDob.setOnClickListener{
//            val datePickerDialog = DatePickerDialog(
//                this,
//                R.style.Theme_Holo_Dialog_MinWidth,
//                listener,
//                year,
//                month,
//                day
//            )
//            datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            datePickerDialog.show()
//        }
//        listener = OnDateSetListener { view, year, month, dayOfMonth ->
//            var month = month
//            month = month + 1
//            val date = "$dayOfMonth-$month-$year"
//            val today = LocalDate.now()
//            if (today.year < year || today.monthValue < month || today.dayOfMonth < dayOfMonth) Toast.makeText(
//                applicationContext, "Enter vaild date.", Toast.LENGTH_SHORT
//            ).show() else binding.createDob.setText(date)
//        }
        binding.createDob.setOnClickListener(View.OnClickListener {
            val Tag = binding.createTag.getText().toString().trim()
            val Dob = binding.createDob.getText().toString().trim()
            val Place = binding.createBirthPlace.getText().toString().trim()
            val Origin = binding.createOrigin.getText().toString().trim()
            val id = UUID.randomUUID().toString()
            if (Tag.isEmpty() || Dob.isEmpty() || Place.isEmpty() || Origin.isEmpty()) Toast.makeText(
                applicationContext, "All fields are required", Toast.LENGTH_SHORT
            ).show() else {
                val documentReference = firestore!!.collection("Patients").document(
                    firebaseUser!!.uid
                ).collection(category!!).document(id)
                val record: MutableMap<String, Any> = HashMap()
                record["id"] = id
                record["Tag"] = Tag
                record["Dob"] = Dob
                record["Origin"] = Origin
                record["Place"] = Place
                //    record.put("Age","Set on display");
                documentReference.set(record).addOnSuccessListener {
                    Toast.makeText(applicationContext, "Record added!", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this, Homepage::class.java))
                }.addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "Failed to record data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}