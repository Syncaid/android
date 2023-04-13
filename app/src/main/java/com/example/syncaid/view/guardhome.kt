package com.example.syncaid.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.syncaid.R
import com.example.syncaid.databinding.ActivityGuardhomeBinding

public class guardhome : AppCompatActivity() {

    private lateinit var binding: ActivityGuardhomeBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuardhomeBinding.inflate(layoutInflater);
        setContentView(binding.root)
        replacefragment(homeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replacefragment(homeFragment())
                R.id.settings -> replacefragment(settingsFragment())
                R.id.devices -> replacefragment(deviceFragment())
                R.id.history -> replacefragment(historyFragment())

                else -> {

                }
            }
            true
        }

    }

    private fun replacefragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }


}