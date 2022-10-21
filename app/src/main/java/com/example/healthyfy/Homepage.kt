package com.example.healthyfy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.healthyfy.databinding.ActivityHomepageBinding

class Homepage : AppCompatActivity() {
    private lateinit var binding: ActivityHomepageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_homepage)
        replaceFragment(HomeFragment())
        binding.bottomNav.setOnItemSelectedListener {

            when(it.itemId){

                R.id.home ->  replaceFragment(HomeFragment())
                R.id.bottom_search -> replaceFragment(SearchFragment())
                R.id.medications -> replaceFragment(MedicationFragment())
                R.id.profile -> replaceFragment(ProfileFragment())

                else -> {}

            }
            true


        }
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layoutForFragment,fragment)
        fragmentTransaction.commit()
    }
}