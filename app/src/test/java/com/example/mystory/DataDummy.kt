package com.example.mystory

import com.example.mystory.data.ListItem

object DataDummy {
    fun generateDummyStoryResponse(): List<ListItem> {
        val items: MutableList<ListItem> = arrayListOf()

        for (i in 0..100) {
            val story = ListItem (
                i.toString(),
                "author + $i",
                "name $i",
                "desc $i",
                "lon + $i",
                "id $i"
            )
            items.add(story)
        }
        return items
    }
}