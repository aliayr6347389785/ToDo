package com.uli.todo.ui.fragment.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uli.todo.data.model.NoteModel
import com.uli.todo.databinding.ItemNoteBinding

class NoteAdapter(private val listener: EditNoteListener)
    : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var list: List<NoteModel> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<NoteModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun getList(): List<NoteModel> {
        return list
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(list[position])
        holder.itemView.setOnClickListener {
            listener.update(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int = list.size

    class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(noteModel: NoteModel) {
            binding.itemTvTitle.text = noteModel.title
            binding.itemTvDescription.text = noteModel.description
        }
    }

    interface EditNoteListener {
        fun update(position: Int)
    }
}