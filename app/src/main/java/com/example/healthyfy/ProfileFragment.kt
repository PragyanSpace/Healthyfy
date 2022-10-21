package com.example.healthyfy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    lateinit var logout: Button
    lateinit var firebaseAuth:FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_profile, container, false)
        firebaseAuth=FirebaseAuth.getInstance()
        logout=view.findViewById(R.id.LogoutBtn)
        logout.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent (getActivity(), MainActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }

        return view
    }
}