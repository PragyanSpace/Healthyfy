package com.example.healthyfy

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.healthyfy.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    lateinit var binding:ActivitySignUpBinding
    private var firebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.goToLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.signUpButton.setOnClickListener{
            val mail = binding.signUpEmail.getText().toString().trim()
            val password = binding.signUpPassword.getText().toString().trim()
            if (mail.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "All fields are required", Toast.LENGTH_SHORT)
                    .show()
            } else if (password.length < 8) {
                Toast.makeText(
                    applicationContext,
                    "Password must be of at least 8 characters",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                firebaseAuth!!.createUserWithEmailAndPassword(mail, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Registration Successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            sendEmailVerification()
                        } else Toast.makeText(
                            applicationContext,
                            "Failed to Register",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
    }

    private fun sendEmailVerification() {
        val firebaseUser = firebaseAuth!!.currentUser
        firebaseUser?.sendEmailVerification()?.addOnCompleteListener {
            Toast.makeText(
                applicationContext,
                "Verification email sent. Verify and login.",
                Toast.LENGTH_SHORT
            ).show()
            firebaseAuth!!.signOut()
            finish()
            startActivity(Intent(this,MainActivity::class.java))
        }
            ?: Toast.makeText(
                applicationContext,
                "Failed to send verification Email.",
                Toast.LENGTH_SHORT
            ).show()
    }
}