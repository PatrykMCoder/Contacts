package com.pmprogramms.contacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pmprogramms.contacts.databinding.ActivityContactDetalisBinding

class ContactDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityContactDetalisBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}