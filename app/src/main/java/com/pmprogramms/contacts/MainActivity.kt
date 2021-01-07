package com.pmprogramms.contacts

import android.content.ContentResolver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pmprogramms.contacts.recyclerview.ContactsObject
import com.pmprogramms.contacts.recyclerview.RecyclerViewAdapter

class MainActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var adapter: RecyclerViewAdapter? = null

    var arrayContacts = arrayListOf<ContactsObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_contacts)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.setHasFixedSize(true)

        val contentResolver = contentResolver

        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null, null
        )

        cursor.use { c ->
            if (c != null) {
                c.moveToFirst()
                while (c.moveToNext()) {
                    var id =
                        c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID))

                    var name =
                        c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))

                    val number = readFromNumber(contentResolver, id)

                    val imageUri : String? = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                    if (imageUri != null)
                        arrayContacts.add(ContactsObject(name, number, imageUri))
                    else
                        arrayContacts.add(ContactsObject(name, number, null))
                    c.moveToNext()
                }
            }
        }

        adapter = RecyclerViewAdapter(arrayContacts)
        recyclerView!!.adapter = adapter
    }
}


fun readFromNumber(contentResolver: ContentResolver, ID: String): String {
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