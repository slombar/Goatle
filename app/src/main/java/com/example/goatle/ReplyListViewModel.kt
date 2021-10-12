package com.example.goatle

import androidx.lifecycle.ViewModel

class ReplyListViewModel : ViewModel(){

    val replys = mutableListOf<Reply>()
    init {
        for (i in 0 until 5) {
            val reply = Reply()

            reply.replyContent = "This class is hard"
            replys += reply
        } }
}