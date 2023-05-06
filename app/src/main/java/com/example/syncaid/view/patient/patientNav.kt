package com.example.syncaid.view.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.syncaid.R
import com.example.syncaid.databinding.ActivityPatientnavBinding
<<<<<<< HEAD
=======
import com.example.syncaid.view.shared.settingsFragment
>>>>>>> origin/dev

public class patientNav : AppCompatActivity() {

    private lateinit var binding: ActivityPatientnavBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientnavBinding.inflate(layoutInflater);
        setContentView(binding.root)
        replacefragment(patientHomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replacefragment(patientHomeFragment())
<<<<<<< HEAD
                R.id.settings -> replacefragment(patientSettingsFragment())
=======
                R.id.settings -> replacefragment(settingsFragment())
>>>>>>> origin/dev
                R.id.devices -> replacefragment(patientDeviceFragment())
                R.id.history -> replacefragment(patientHistoryFragment())

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