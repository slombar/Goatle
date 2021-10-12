package com.example.goatle

import java.sql.Date

class PostModel {



    private var postId: Int? = null
    private var postDate: String? = null
    private var postUsername: String? = null
    private var postContent: String? = null




    // creating getter and setter methods
    fun getPostId(): Int? {
        return postId
    }

    fun setPostId(courseName: Int?) {
        this.postId = courseName
    }

    fun getPostDate(): String? {
        return postDate
    }

    fun setPostDate(postDate2: String?) {
        this.postDate = postDate2
    }

    fun getPostUsername(): String? {
        return postUsername
    }

    fun setPostUsername(postUserName: String?) {
        this.postUsername = postUserName
    }

    fun getPostContent(): String? {
        return postContent
    }

    fun setPostContent(postContent2: String?) {
        this.postContent = postContent2
    }


}