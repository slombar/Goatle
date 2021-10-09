package com.example.goatle

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

private const val TAG = "ChatFragmentTag"
private  val KEY_NICKNAME = "nickname_of_user"


class ChatFragment : Fragment() {
    val username = "testUsername"

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Chat Fragment Started")
    }

    override fun onSaveInstanceState(outState:Bundle){
        super.onSaveInstanceState(outState)
        outState.putString(KEY_NICKNAME, username)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.chat_layout, container, false)
        //TODO assign buttons

        return view

    }
}