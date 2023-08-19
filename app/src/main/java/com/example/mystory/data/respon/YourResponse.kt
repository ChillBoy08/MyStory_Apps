package com.example.mystory.data.respon

import androidx.room.Entity
import com.example.mystory.data.ListItem
import com.google.gson.annotations.SerializedName

@Entity(tableName = "story")
data class YourResponse(

    @field:SerializedName("listStory")
    val listStory: List<ListItem>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

)