package com.example.healthyfy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.healthyfy.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : Fragment() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    lateinit var firebaseUser: FirebaseUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_profile, container, false)
        // Inflate the layout for this fragment
//        val view:View = inflater.inflate(R.layout.fragment_profile, container, false)
        firebaseAuth= FirebaseAuth.getInstance()
        firestore= FirebaseFirestore.getInstance()
        firebaseUser= firebaseAuth.currentUser!!


        val documentReference = firestore!!.collection("Patients").document(firebaseUser!!.uid)
        documentReference.addSnapshotListener { snapshot, e ->

        }








        return binding.root
    }
}