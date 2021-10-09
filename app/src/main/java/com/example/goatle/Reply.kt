package com.example.goatle

import java.util.*

data class Reply(
    val id: UUID = UUID.randomUUID(), //each post has a unique ID, should it contain a post object
    var title: String = "", //users name
    var date: Date = Date(),
    var relpContent: String = ""
)