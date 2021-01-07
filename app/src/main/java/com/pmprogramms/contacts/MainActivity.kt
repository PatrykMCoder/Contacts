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

        try {
            if (cursor != null) {
                cursor.moveToFirst()
                while (cursor.moveToNext()) {
                    var id =
                        cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID))

                    var name =
                        cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))

                    var number = readFromNumber(contentResolver, id)

                    arrayContacts.add(ContactsObject(name, number))
                    cursor.moveToNext()
                }
            }

        } finally {
            cursor!!.close()
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