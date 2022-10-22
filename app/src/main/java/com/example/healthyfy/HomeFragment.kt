package com.example.healthyfy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthyfy.databinding.FragmentHomeBinding
import com.example.healthyfy.databinding.FragmentProfileBinding
import com.example.healthyfy.databinding.FragmentSearchBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    lateinit var firebaseUser: FirebaseUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home, container, false)
        // Inflate the layout for this fragment
//        val view:View = inflater.inflate(R.layout.fragment_profile, container, false)
        firebaseAuth= FirebaseAuth.getInstance()
        firestore= FirebaseFirestore.getInstance()
        firebaseUser= firebaseAuth.currentUser!!


        val meds=ArrayList<pData>()
        GlobalScope.launch(Dispatchers.IO)
        {
            val collectionRef = firestore!!.collection("Patients").document(firebaseUser!!.uid).collection("presentMedication")
                .get().addOnSuccessListener {
                    for(medicines in it)
                    {

                        val medName = medicines.get("medicine_name").toString()
                        val pillsPerDay = medicines.get("pillsPerDay").toString()
                        val time = medicines.get("time").toString()
                        val data=pData(medName,pillsPerDay,time)

                        meds.add(data)
                    }
                }
        }


        binding.recView2.layoutManager= LinearLayoutManager(activity)
        val adapter= PatAdapter(meds)

        binding.recView2.adapter=adapter


//        if(count == 0) binding.showMedicationStatusText.text = "You don't have any medications right Now"
        adapter.setOnItemClickListener(object: PatAdapter.onItemClickListener {
            override fun onItemClicked(position: Int) {
                Toast.makeText(activity,"Take this med",Toast.LENGTH_SHORT).show()
            }
        })




        return binding.root
    }
}