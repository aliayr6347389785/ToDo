package com.uli.todo.ui.fragment.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.uli.todo.R
import com.uli.todo.base.BaseFragment
import com.uli.todo.databinding.FragmentHomeBinding
import com.uli.todo.ui.App
import com.uli.todo.ui.fragment.home.adapter.NoteAdapter
import java.util.*


class HomeFragment
    : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    NoteAdapter.EditNoteListener {
    private lateinit var adapter: NoteAdapter

    override fun setupUI() {
        adapter = NoteAdapter(this)
        binding.rvNote.adapter = adapter
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                Toast.makeText(requireContext(), "on Move", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                Toast.makeText(requireContext(), "on Swiped ", Toast.LENGTH_SHORT).show()
                val position = viewHolder.adapterPosition
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle("")
                dialog.setNegativeButton("нет", null)
                dialog.setPositiveButton("да"
                ) { p0, p1 ->
                    val model = adapter.getList().get(position)
                    App.db.noteDao()?.delete(model)
                }
                dialog.show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvNote)

    }

    override fun setupObserver() {
        super.setupObserver()
        loadData()
        initListener()
    }

    private fun loadData() {
        App.db.noteDao()?.getAllNote()?.let {
            adapter.setList(it)
        }
    }

    private fun initListener() {
        binding.btnAddNote.setOnClickListener {
            controller.navigate(R.id.addNoteFragment)
        }
    }

    override fun update(position: Int) {
        val bundle = Bundle()
        val model = adapter.getList().get(position)
        model.id?.let { bundle.putInt("id", it) }
        bundle.putSerializable("editNote", model)
        controller.navigate(R.id.addNoteFragment, bundle)
    }
}
