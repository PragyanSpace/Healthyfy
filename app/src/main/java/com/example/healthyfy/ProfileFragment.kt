package com.example.healthyfy


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.healthyfy.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder


class ProfileFragment : Fragment() {
    lateinit var firebaseAuth:FirebaseAuth
    lateinit var firestore:FirebaseFirestore
    lateinit var firebaseUser:FirebaseUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_profile, container, false)
        // Inflate the layout for this fragment
//        val view:View = inflater.inflate(R.layout.fragment_profile, container, false)
        firebaseAuth=FirebaseAuth.getInstance()
        firestore=FirebaseFirestore.getInstance()
        firebaseUser= firebaseAuth.currentUser!!


        val documentReference = firestore!!.collection("Patients").document(firebaseUser!!.uid)
        documentReference.addSnapshotListener { snapshot, e ->
            if (snapshot != null && snapshot.exists()) {
                val code: String = snapshot?.get("id") as String
                binding.bloodGrp=snapshot?.get("blood") as String
                binding.dob=snapshot?.get("Dob") as String
                binding.contact=snapshot?.get("contact") as String
                binding.preD=snapshot?.get("preD") as String
                binding.nameFull=snapshot?.get("name") as String
                var writer=MultiFormatWriter()
                try {
                    val matrix:BitMatrix=writer.encode(code,BarcodeFormat.QR_CODE,512,512)
                    val encoder=BarcodeEncoder()
                    val bmp=encoder.createBitmap(matrix)
                    binding.QR.setImageBitmap(bmp)
                }
                catch (e:Exception)
                {

                }
            }
        }






        binding.LogoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent (getActivity(), MainActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }

        return binding.root
    }
}