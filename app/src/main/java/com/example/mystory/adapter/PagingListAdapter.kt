package com.example.mystory.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mystory.HiFi.detail.DetailActivity
import com.example.mystory.data.ListItem
import com.example.mystory.databinding.ItemStoryBinding

class PagingListAdapter : PagingDataAdapter<ListItem, PagingListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(
        holder: PagingListAdapter.MyViewHolder,
        position: Int) {

        val data = getItem(position)
        if (data != null) { holder.bind(data) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PagingListAdapter.MyViewHolder {

       val binding =
           ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    class MyViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListItem) {
            binding.tvRvName.text = data.name
            binding.tvRvDesc.text = data.description

            Glide.with(itemView.context)
                .load(data.photoUrl)
                .into(binding.imgStory)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_STORY, data)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.imgStory, "image"),
                        Pair(binding.tvRvName, "name"),
                        Pair(binding.tvRvCreate, "create"),
                        Pair(binding.tvRvDesc, "desc")
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListItem>() {

            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}