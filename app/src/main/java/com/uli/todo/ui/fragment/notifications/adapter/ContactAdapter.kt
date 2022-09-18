package com.uli.todo.ui.fragment.notifications.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uli.todo.data.model.contactModel.ContactModel
import com.uli.todo.databinding.ItemContactBinding

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private var list = listOf<ContactModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setContactList(list: List<ContactModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    class ContactViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(model: ContactModel) {
            binding.itemTvContactName.text = model.name
            binding.itemTvContactNumber.text = model.number.toString()
        }

    }
}