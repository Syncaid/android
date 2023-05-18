package com.example.syncaid.view.shared

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.syncaid.MainActivity
import com.example.syncaid.R
import com.example.syncaid.view.patient.ManageGuardians
import com.example.syncaid.viewmodel.userViewModel
import com.google.android.material.snackbar.Snackbar

class settingsFragment : PreferenceFragmentCompat() {

    val UserViewModel = userViewModel()

    fun areNotificationsPermissionsGranted(context: Context?): Boolean {
        val notificationPermission = context?.let {
            ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.POST_NOTIFICATIONS
            )
        } == PackageManager.PERMISSION_GRANTED

        // Return true if all three permissions are granted
        return notificationPermission
    }

    fun showNotificationPermissionDialog(context: Context) {
        val alertDialog = android.app.AlertDialog.Builder(context)
            .setTitle("Notification Permission Required")
            .setMessage("This app requires notification permissions to function properly. Please go to settings and grant the notification permission then RESTART the app.")
            .setPositiveButton("Go to Settings") { dialog, which ->
                goToSettings(context)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                val notification = findPreference<SwitchPreferenceCompat>("notification")
                notification?.isChecked = false
                dialog.dismiss()
            }
            .create()
        alertDialog.show()

    }

    fun goToSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", context.packageName, null)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val appInfo = context.packageManager.getApplicationInfo(context.packageName, 0)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.putExtra("app_package", context.packageName)
        intent.putExtra("app_uid", appInfo.uid)
        context.startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onResume() {
        super.onResume()
        val notification = findPreference<SwitchPreferenceCompat>("notification")
        notification?.isChecked = areNotificationsPermissionsGranted(context);
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val userPrefs = requireContext().getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
        val userPrefsEditor = userPrefs.edit()

        val sharedPrefs = requireContext().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        val id = sharedPrefs.getString("ID","null")
        val isdarkmode = userPrefs.getBoolean("DARKMODE",false)

        val editprofile = findPreference<Preference>("edit")
        val qr = findPreference<Preference>("qr")
        val logout = findPreference<Preference>("logout")
        val changepw = findPreference<Preference>("changepassword")
        val manageGuardians = findPreference<Preference>("manageguardians")
        val darkmode = findPreference<SwitchPreferenceCompat>("darkmode")
        val notification = findPreference<SwitchPreferenceCompat>("notification")

        notification?.isChecked = areNotificationsPermissionsGranted(context);
        darkmode?.isChecked = isdarkmode

        manageGuardians?.setOnPreferenceClickListener {

            startActivity(Intent(requireContext(), ManageGuardians::class.java))
            true
        }

        changepw?.setOnPreferenceClickListener {
            startActivity(Intent(requireContext(), changePassword::class.java))
            true
        }

        editprofile?.setOnPreferenceClickListener {
            startActivity(Intent(requireContext(), editProfile::class.java))
            true
        }


        qr?.setOnPreferenceClickListener {
            startActivity(Intent(requireContext(), QR::class.java))
            true
        }

        notification?.setOnPreferenceChangeListener { preference, newValue ->
            val isChecked = newValue as Boolean
            if (isChecked) {
                context?.let { showNotificationPermissionDialog(it) };
            } else {

            }
            true
        }

        darkmode?.setOnPreferenceChangeListener { preference, newValue ->

            val isChecked = newValue as Boolean
            if(isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                userPrefsEditor.putBoolean("DARKMODE", true)
                userPrefsEditor.apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                userPrefsEditor.putBoolean("DARKMODE", false)
                userPrefsEditor.apply()
            }

            true
        }


        logout?.setOnPreferenceClickListener {
            val rootView = requireView()
            val builder = context?.let { it1 -> AlertDialog.Builder(it1) }
            builder?.setTitle("Logout")
            builder?.setMessage("Are you sure you want to log out ?")
            builder?.setIcon(R.drawable.logout)
            builder?.setPositiveButton("Logout") { dialog, which ->
                UserViewModel.logout(id.toString()) { response, code ->
                    if (code == 200) {
                        Log.d("SUCCESS", "Response: $response")

                        editor.clear()
                        editor.putBoolean("LOGGEDIN", false)
                        editor.apply()
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)

                    } else {
                        Snackbar.make(rootView, "Logout failed", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red_accent))
                            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                            .show()
                        Log.e("ERROR", "Error: API call failed")
                    }
                }
            }
            builder?.setNegativeButton("Cancel") { dialog, which ->
                // Do something when the Cancel button is clicked
            }

            val dialog = builder?.create()
            dialog?.show()

        true
        }

    }





}