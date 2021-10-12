package com.example.goatle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostListViewModel: ViewModel() {

    val posts = mutableListOf<Post>()
    init {
        for (i in 0 until 5) {
            val post = Post()
            post.username = "Happy_Hippo"
            post.date = "October 10, 2021"
            post.postContent = "This class is hard"
            posts += post
        } }


}