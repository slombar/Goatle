package com.example.goatle

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Post(@PrimaryKey val id: UUID = UUID.randomUUID(),
                var title: String = "",
                var date: Date = Date(),
                var postContent: String = "",
                var isSolved: Boolean = false) {

}