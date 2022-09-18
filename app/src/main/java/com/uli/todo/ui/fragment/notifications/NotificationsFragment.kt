package com.uli.todo.ui.fragment.notifications

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.provider.ContactsContract
import androidx.core.content.ContextCompat.checkSelfPermission
import com.uli.todo.base.BaseFragment
import com.uli.todo.data.model.contactModel.ContactModel
import com.uli.todo.databinding.FragmentNotificationsBinding
import com.uli.todo.ui.fragment.notifications.adapter.ContactAdapter


class NotificationsFragment :
    BaseFragment<FragmentNotificationsBinding>(FragmentNotificationsBinding::inflate) {
    private lateinit var adapter: ContactAdapter

    override fun setupUI() {
        adapter = ContactAdapter()
        binding.rcContact.adapter = adapter
    }

    override fun setupObserver() {
        super.setupObserver()
        initContact()
    }

    @SuppressLint("Range")
    private fun initContact() {
        val list = arrayListOf<ContactModel>()

        val contentResolver = requireActivity().contentResolver
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )
        if (cursor?.count!! > 0) {
            while (cursor.moveToNext()) {
                val hasPhoneNumber: Int =
                    cursor.getString(
                        cursor
                            .getColumnIndex(
                                ContactsContract.Contacts.HAS_PHONE_NUMBER
                            )
                    )
                        .toInt()
                if (hasPhoneNumber > 0) {
                    val id: String =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                    val phoneCursor: Cursor? = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )
                    if (phoneCursor?.moveToNext()!!) {
                        val phoneNumber =
                            phoneCursor.getString(
                                phoneCursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER
                                )
                            )
                        phoneCursor.close()
                        list.add(ContactModel(name, phoneNumber))
                    }
                    phoneCursor.close()
                }
            }
            cursor.close()
            adapter.setContactList(list)
        }
    }
}