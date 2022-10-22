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
import com.example.healthyfy.databinding.FragmentProfileBinding
import com.example.healthyfy.databinding.FragmentSearchBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    lateinit var firebaseUser: FirebaseUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSearchBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_search, container, false)
        // Inflate the layout for this fragment
//        val view:View = inflater.inflate(R.layout.fragment_profile, container, false)
        firebaseAuth= FirebaseAuth.getInstance()
        firestore= FirebaseFirestore.getInstance()
        firebaseUser= firebaseAuth.currentUser!!


        val hos=ArrayList<HData>()
        GlobalScope.launch(Dispatchers.IO)
        {
            val collectionRef = firestore!!.collection("Managements")
            .get().addOnSuccessListener {
                for(hospitals in it)
                {
                    val id=hospitals.id
                    val hname=hospitals.get("hospital_name").toString()
                    val hloc=hospitals.get("location").toString()
                    val hcontact=hospitals.get("contact").toString()
                    val data=HData(hname,hloc,hcontact,id)
                    hos.add(data)
                }
            }
        }


        binding.recView.layoutManager= LinearLayoutManager(activity)
        val adapter= MyAdapter(hos)
        binding.recView.adapter=adapter
        adapter.setOnItemClickListener(object: MyAdapter.onItemClickListener {
            override fun onItemClicked(position: Int) {
                val intent =Intent(activity, HospitalDetails::class.java)
                intent.putExtra("hos_details",hos[position])
                startActivity(intent)
            }
        })




        return binding.root
    }
}