package com.uli.todo.ui.onBoard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uli.todo.R
import com.uli.todo.databinding.PageBoardBinding

class OnBoardAdapter(private val listener: FinishCallback)
    : RecyclerView.Adapter<OnBoardAdapter.OnBoardViewHolder>() {


    val animationList = listOf(
        R.raw.car_city,
        R.raw.travel_icons,
        R.raw.traveller
    )
    val listTitle = listOf(
        "Слон",
        "Лого",
        "Рандом"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardViewHolder {
        val binding = PageBoardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return OnBoardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnBoardViewHolder, position: Int) {
        holder.onBoard(position)
        holder.binding.btnBoardFinish.setOnClickListener {
            listener.close()
        }
    }

    override fun getItemCount(): Int = listTitle.size

    inner class OnBoardViewHolder(val binding: PageBoardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBoard(position: Int) {
            binding.lottieAnim.setAnimation(animationList[position])
            binding.tvBoardTitle.text = listTitle[position]
        }
    }

    interface FinishCallback {
        fun close()
    }
}