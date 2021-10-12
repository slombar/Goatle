package com.example.goatle

import java.util.*

data class Reply(
    val id: Int = 0, //each post has a unique ID, should it contain a post object
    var replyUsername: String = "", //users name
    var replyDate: String = "",
    var replyContent: String = ""
)