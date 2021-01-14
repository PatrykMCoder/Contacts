package com.pmprogramms.contacts

import android.app.Activity
import android.content.ContentProviderOperation
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.*
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pmprogramms.contacts.databinding.ActivityCreateNewContactBinding
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.ByteArrayOutputStream


class CreateNewContactActivity : AppCompatActivity() {
    private lateinit var firstName: String
    private lateinit var secondName: String
    private lateinit var number: String
    private lateinit var email: String
    private lateinit var binding: ActivityCreateNewContactBinding
    private lateinit var byteArray: ByteArray

    private val GALLERY_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewContactBinding.inflate(layoutInflater)

        binding.imageSelect.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            val mimeType = arrayOf("image/jpeg", "image/png", "image/jpg")
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            startActivityForResult(intent, 1001)
        }

        setContentView(binding.root)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { uri ->
                        launchImageCrop(uri)
                    }
                }
            }

            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    setImage(result.uri)
                    val bmp: Bitmap = MediaStore.Images.Media.getBitmap(
                        contentResolver,
                        result.uri
                    )
                    val stream = ByteArrayOutputStream()
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    byteArray = stream.toByteArray()
                }
            }
        }

    }

    private fun setImage(uri: Uri) {
        binding.imageSelect.setImageURI(uri)
    }

    private fun launchImageCrop(uri: Uri) {
        CropImage.activity(uri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .start(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInf = menuInflater
        menuInf.inflate(R.menu.menu_new_contact, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                val data = binding.name.text.toString().split(" ")
                firstName = data[0]

                secondName = if (data.size == 2)
                    data[1]
                else
                    ""

                email = binding.email.text.trim().toString()

                number = binding.number.text.trim().toString()
                saveContact()
                return true
            }
        }
        return false
    }

    private fun saveContact() {
        val operationList = ArrayList<ContentProviderOperation>()
        operationList.add(
            ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build()
        )

        operationList.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
                .withValue(StructuredName.GIVEN_NAME, secondName)
                .withValue(StructuredName.FAMILY_NAME, firstName)
                .build()
        )

        operationList.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
                .withValue(Phone.NUMBER, number)
                .withValue(Phone.TYPE, Phone.TYPE_HOME)
                .build()
        )

        operationList.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
                .withValue(Email.DATA, email)
                .withValue(Email.TYPE, Email.TYPE_WORK)
                .build()
        )
        operationList.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, Photo.CONTENT_ITEM_TYPE)
                .withValue(Photo.PHOTO, byteArray)
                .build()
        )
        try {
            contentResolver.applyBatch(ContactsContract.AUTHORITY, operationList)
            finish()
        } catch (e: Exception) {
            Toast.makeText(
                baseContext,
                "Something wrong, try again. If you select image - please crop more photo. It's too big",
                Toast.LENGTH_LONG
            ).show()
            e.printStackTrace()
            Log.d("Something wrong, try again", "saveContact: ${e.message}")
        }
    }
}