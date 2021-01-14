package com.pmprogramms.contacts.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.pmprogramms.contacts.R
import com.pmprogramms.contacts.dialog.AddMessageDialog
import com.pmprogramms.contacts.dialog.OpenSourcesDialog
import com.pmprogramms.contacts.dialog.PrivacyDialog


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val fastMessage: Preference? = findPreference("add_fast_msg")
        val feedback: Preference? = findPreference("feedback")
        val privacy: Preference? = findPreference("privacy")
        val openSource: Preference? = findPreference("sources")

        fastMessage?.setOnPreferenceClickListener {
            AddMessageDialog().show(parentFragmentManager, "fragmentdialog4")
            return@setOnPreferenceClickListener true
        }

        feedback?.setOnPreferenceClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:pmarciszewski774@gmail.com")
            startActivity(emailIntent)
            return@setOnPreferenceClickListener true
        }

        privacy?.setOnPreferenceClickListener {
            PrivacyDialog().show(parentFragmentManager, "fragmentdialog2")
            return@setOnPreferenceClickListener true
        }

        openSource?.setOnPreferenceClickListener {
            OpenSourcesDialog().show(parentFragmentManager, "fragmentdialog3")
            return@setOnPreferenceClickListener true
        }
    }
}