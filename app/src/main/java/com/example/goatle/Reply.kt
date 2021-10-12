package com.example.goatle

import java.util.*

data class Reply(
    val id: UUID = UUID.randomUUID(), //each post has a unique ID, should it contain a post object
    var username: String = "", //users name
    var date: String = "",
    var replyContent: String = "",
    var referencePost: Int = 0
)