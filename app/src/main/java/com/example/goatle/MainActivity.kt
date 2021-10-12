package com.example.goatle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Connection
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "MainActivityTag"


class MainActivity : AppCompatActivity(),  HomeFragment.Callbacks, PostFragment.Callbacks, ReplyFragment.Callbacks {

    private  var ip : String = ""
    private var port : String = "1433"
    private var Class: String = "net.sourceforge.jtds.jdbc.Driver"
    private var database : String = "Content"
    private var username : String = "sa"
    private var password : String = "Mobile2021"
    private var url : String = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database

    private var connection: Connection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            Log.d(TAG, "The current fragment is null idiot moron")
            val fragment = HomeFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()

        }

        //set up reclyviewer
    }

    override fun onCreatePostSelected() {
        val fragment = PostFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onExitSelected() {
        val fragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun onHomeButtonClicked(){
        val fragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }

    fun onChatButtonClicked(){
        val fragment = ChatFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }
    fun addRecord(view: View, usernameText: String, postText: String, contentText: String){
        val username = usernameText
        val date = postText
        val content = contentText
        val dataBaseHandler : DatabaseHandler = DatabaseHandler(this)
        if(!username.isEmpty() && !date.isEmpty() && !content.isEmpty()){
            val status = dataBaseHandler.addPost(Post(0, username, date, content))
            if(status > -1){
                Toast.makeText(applicationContext, "Record Saved", Toast.LENGTH_LONG)

                //missing a function dealing with reclye viewer
            }
        }
        else{
            Toast.makeText(applicationContext, "Cannot be blank", Toast.LENGTH_LONG).show()
        }

    }
    override fun onPostSelected() {
        val fragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }



    override fun onReplyClicked(id: String) {
        val fragment = ReplyFragment.newInstance(id)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onReplyPostSelected(){
        val fragment = PostFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

    }


}