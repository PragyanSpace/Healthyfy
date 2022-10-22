package com.example.healthyfy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.healthyfy.databinding.ActivityHospitalDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class HospitalDetails : AppCompatActivity() {
    private lateinit var binding: ActivityHospitalDetailsBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var data:HData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_hospital_details)
        firebaseAuth=FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser!!
        firestore = FirebaseFirestore.getInstance()
        if(intent.extras!=null)
        {
            data= intent.extras!!.getParcelable<HData>("hos_details")!!
        }
        val hospitalId = data.id

        binding.hname=data.hname
        binding.hloc=data.hloc
        binding.hnum=data.hnum

        binding.makeAppoinmentBtn.setOnClickListener{
            val aptId= UUID.randomUUID().toString().trim()
            val documentReference = firestore!!.collection("Managements").document(hospitalId.toString()).collection("appoinments").document(aptId)
            val aptRecord: MutableMap<String, Any> = HashMap()
            aptRecord["pat_id"] = firebaseUser!!.uid
            aptRecord["status"] = 0
            GlobalScope.launch(Dispatchers.IO)
            {
                documentReference.set(aptRecord).addOnSuccessListener {
                    Toast.makeText(applicationContext, "Appointment Request Send!", Toast.LENGTH_SHORT)
                        .show()
                }.addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "Failed to send appoinment request! Please try again",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
