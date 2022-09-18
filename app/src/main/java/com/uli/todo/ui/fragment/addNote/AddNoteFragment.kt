package com.uli.todo.ui.fragment.addNote

import com.uli.todo.base.BaseFragment
import com.uli.todo.data.model.NoteModel
import com.uli.todo.databinding.FragmentAddNoteBinding
import com.uli.todo.ui.App

class AddNoteFragment :
    BaseFragment<FragmentAddNoteBinding>(FragmentAddNoteBinding::inflate) {

    override fun setupUI() {
    }

    override fun setupObserver() {
        super.setupObserver()
        checkIsEdit()
    }

    private fun checkIsEdit() {
        if (arguments != null) {
            binding.btnSave.text = "Edit"
            val model: NoteModel = arguments?.getSerializable("editNote") as NoteModel
            binding.etTitle.setText(model.title)
            binding.etDes.setText(model.description)
            binding.btnSave.setOnClickListener {
                editNote()
            }
        } else {
            binding.btnSave.setOnClickListener {
                saveNote()
            }
        }
    }

    private fun saveNote() {
        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val des = binding.etDes.text.toString()
            App.db.noteDao()?.addNote(NoteModel(title = title, description = des))
            controller.navigateUp()
        }
    }

    private fun editNote() {
        val title = binding.etTitle.text.toString()
        val des = binding.etDes.text.toString()
        val id = arguments?.getInt("id")
        App.db.noteDao()?.updateNote(NoteModel(id = id, title = title, description = des))
        controller.navigateUp()
    }
}