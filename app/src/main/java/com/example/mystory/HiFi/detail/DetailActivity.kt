package com.example.mystory.HiFi.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mystory.R
import com.example.mystory.data.ListItem
import com.example.mystory.data.respon.ListStoryItem
import com.example.mystory.databinding.ActivityDetailBinding
import com.example.mystory.withDateFormat

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = resources.getString(R.string.app_nameDetail)

        val story = intent.getParcelableExtra<ListItem>(EXTRA_STORY)

        binding.apply {
            tvNameDetail.text = story?.name
            tvCreated.withDateFormat(story?.createdAt.toString())
            tvDesc.text = story?.description
        }

        Glide.with(this)
            .load(story?.photoUrl)
            .into(binding.imgDetail)

    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}