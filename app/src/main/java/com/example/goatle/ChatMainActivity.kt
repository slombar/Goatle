package com.example.goatle
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent


private const val REQUEST_CODE_CHAT = 0

class ChatMainActivity : AppCompatActivity() {

    lateinit var btnLogin : Button
    lateinit var username : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.global_chat_main)

        btnLogin = findViewById(R.id.btnLogin)
        username = findViewById(R.id.username_global_chat)

        btnLogin.setOnClickListener {
            if (username.text.isNotEmpty()) {
                val user = username.text.toString()

                App.user = user

                val intent = ChatChatActivity.newIntent(this@ChatMainActivity)
                startActivityForResult(intent, REQUEST_CODE_CHAT)

                startActivity(intent, savedInstanceState)
            } else {
                Toast.makeText(applicationContext,"Username should not be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}