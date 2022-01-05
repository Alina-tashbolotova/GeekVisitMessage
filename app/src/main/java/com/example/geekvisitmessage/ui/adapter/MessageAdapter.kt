package com.example.geekvisitmessage.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.geekvisitmessage.databinding.ItemMessageBinding
import com.example.geekvisitmessage.ui.MessageModel

class MessageAdapter(
    private val onItemClick: (title:String,image:String) -> Unit
    ) : ListAdapter<MessageModel, MessageAdapter.MessageViewHolder>(
    MessageComparator()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            ItemMessageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class MessageViewHolder(
        private val binding: ItemMessageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                getItem(bindingAdapterPosition)?.let {
                    onItemClick(it.title,it.image)
                }
            }
        }

        fun onBind(item: MessageModel) = with(binding) {
            txtMessage.text = item.title
            Glide.with(binding.itemImage)
                .load(item.image)
                .centerCrop()
                .into(binding.itemImage)
        }

    }
    class MessageComparator: DiffUtil.ItemCallback<MessageModel>(){
        override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
            return oldItem == newItem
        }

    }
}