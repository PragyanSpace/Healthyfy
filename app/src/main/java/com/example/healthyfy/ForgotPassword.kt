package com.example.healthyfy

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.healthyfy.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {
    lateinit var binding:ActivityForgotPasswordBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_forgot_password)
        supportActionBar!!.hide()
        firebaseAuth = FirebaseAuth.getInstance()
        binding.gobacktologin.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.passwordrecover.setOnClickListener{
            val mail = binding.forgotpassword.text.toString().trim()
            if (mail.isEmpty()) {
                Toast.makeText(applicationContext, "Enter your email first.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                //send password recovery mail
                firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Mail Sent, You can recover your password using mail",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                        startActivity(Intent(this, MainActivity::class.java))
                    } else Toast.makeText(
                        applicationContext,
                        "Email not registered",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}