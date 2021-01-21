package com.pmprogramms.contacts

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.pmprogramms.contacts.helper.Permission


class SplashActivity : AppCompatActivity() {

    private val ALL_PERMISSIONS = 101
    val permissions = arrayOf(
        Manifest.permission.CALL_PHONE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.SEND_SMS,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        if (!Permission().hasPermissions(this, permissions)) {
            Toast.makeText(
                this,
                "Please accept all permission for correctly working application. If you are confused about permission please read Privacy Policy. Application is not collect your data.",
                Toast.LENGTH_LONG
            ).show()
            ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
        } else {
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(mainActivityIntent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ALL_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val mainActivityIntent = Intent(this, MainActivity::class.java)
                    mainActivityIntent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(mainActivityIntent)
                } else {
                    Toast.makeText(
                        this,
                        "PERMISSIONS Denied, please accept all permissions",
                        Toast.LENGTH_LONG
                    ).show()
                    ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
                }
            }
        }
    }
}