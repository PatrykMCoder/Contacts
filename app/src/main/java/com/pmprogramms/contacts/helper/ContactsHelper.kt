package com.pmprogramms.contacts.helper

import android.content.ContentResolver
import android.provider.ContactsContract
import com.pmprogramms.contacts.helper.objects.ContactsObject

class ContactsHelper {

    companion object {
        val instance = ContactsHelper()
    }

    fun getDataAboutNumbers(contentResolver: ContentResolver) : ArrayList<ContactsObject> {
        val arrayData = arrayListOf<ContactsObject>()

        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null, null
        )

        cursor.use { c ->
            if (c != null) {
                c.moveToFirst()
                while (c.moveToNext()) {
                    val id =
                        c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID))

                    val name =
                        c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))

                    val number = readFromNumber(contentResolver, id)

                    val imageUri : String? = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                    if (imageUri != null)
                        arrayData.add(ContactsObject(name, number, imageUri))
                    else
                        arrayData.add(ContactsObject(name, number, null))
                    c.moveToNext()
                }
            }
        }
        return arrayData
    }

    private fun readFromNumber(contentResolver: ContentResolver, ID: String): String {
        val phoneCursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
            arrayOf(ID),
            null
        )
        var number = ""
        if (phoneCursor!!.moveToFirst())
            number =
                phoneCursor.getString(phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))

        phoneCursor.close()
        return number
    }
}