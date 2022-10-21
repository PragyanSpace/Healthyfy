package com.example.healthyfy

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.healthyfy.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser= firebaseAuth.currentUser
        if (firebaseUser != null) {
            finish()
            startActivity(Intent(this@MainActivity, Homepage::class.java))
        }
        binding.signinBtn.setOnClickListener{
            val intent = Intent(this@MainActivity, SignUp::class.java)
            startActivity(intent)
        }
        binding.forgotpassword.setOnClickListener{
            val intent = Intent(this@MainActivity,ForgotPassword::class.java)
            startActivity(intent)
        }
        binding.loginBtn.setOnClickListener{
            val mail = binding.loginEmail.text.toString().trim { it <= ' ' }
            val password = binding.loginPassword.text.toString()
            if (mail.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "All fields are required", Toast.LENGTH_SHORT)
                    .show()
            } else {
                //firebase work
                firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener{ task ->
                        if (task.isSuccessful) {
                            checkMailVerification()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Account doesn't exist",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

    private fun checkMailVerification() {
        val firebaseUser= firebaseAuth.currentUser
        if (firebaseUser != null) {
            if (firebaseUser.isEmailVerified) {
                Toast.makeText(applicationContext, "Logged in", Toast.LENGTH_SHORT).show()
                finish()
                startActivity(Intent(this@MainActivity, Homepage::class.java))
            } else {
                Toast.makeText(applicationContext, "verify your mail first.", Toast.LENGTH_SHORT).show()
                firebaseAuth.signOut()
            }
        }
    }
}