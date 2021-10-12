package com.example.goatle

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.function.IntToDoubleFunction

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_Name, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_Name = "Content"
        private const val DATABASE_Table = "Posts"

        private const val postID = "_id"
        private const val postUsername = "username"
        private const val postDate = "Date"
        private const val postContent = "Content"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_POSTS_TABLE = ("CREATE TABLE " + DATABASE_Table + "("+
                postID + "INTEGER PRIMARY KEY," + postUsername + "TEXT," +
                postDate + "Text," + postContent + "TEXT" +")"
                )
        db?.execSQL(CREATE_POSTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS" + DATABASE_Table)
        onCreate(db)
    }

    fun addPost(post : Post): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(postID, post.id)
        contentValues.put(postUsername, post.postUsername)
        contentValues.put(postDate, post.postDate)
        contentValues.put(postContent, post.postContent)

        val success = db.insert(DATABASE_Table, null, contentValues)
        db.close()

        return success
    }

    @SuppressLint("Range")
    fun viewPost(): ArrayList<Post> {
        val emptyList: ArrayList<Post> = ArrayList<Post>()

        val selectQuery = "SELECT * FROM $DATABASE_Table"

        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLException) {
            db.execSQL(selectQuery)
            return ArrayList()

        }

        var ID: Int
        var username: String
        var Date: String
        var postCont: String

        if (cursor.moveToFirst()) {
            do {
                ID = cursor.getInt(cursor.getColumnIndex(postID))
                username = cursor.getString(cursor.getColumnIndex(postUsername))
                Date = cursor.getString(cursor.getColumnIndex(postDate))
                postCont = cursor.getString(cursor.getColumnIndex(postContent))

                val emp = Post(id = ID, postUsername = username, postDate = Date, postContent = postCont)
                emptyList.add(emp)

            } while (cursor.moveToNext())


    }
        return emptyList

    }

    fun updatePost(post: Post): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(postID, post.id)
        contentValues.put(postUsername, post.postUsername)
        contentValues.put(postDate, post.postDate)
        contentValues.put(postContent, post.postContent)

        val success = db.update(DATABASE_Table, contentValues, postID + post.id, null)

        db.close()
        return success
    }

    fun deletePost(post: Post): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(postID, post.id)

        val success = db.delete(DATABASE_Table, postID + post.id, null)
        db.close()
        return success
    }



}