package com.example.goatle

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment

private const val TAG = "HomeFragmentTag"

private const val KEY_NICKNAME = "key_nickname_home"

class HomeFragment : Fragment() {

    val username = "testUsername"
    private lateinit var chatHomeButton: ImageButton
    private lateinit var newPost: ImageButton

    interface Callbacks {
        //get only one team's games
        fun onHomeButtonClicked()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Chat Fragment Started")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_NICKNAME, username)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //removes the overlapping problem
        container?.removeAllViews()

        val view = inflater.inflate(R.layout.home_bar, container, false)
        //TODO assign buttons


        chatHomeButton = view.findViewById(R.id.chatButtonHomePage)
        newPost = view.findViewById(R.id.newPostButtonHomePage)

        chatHomeButton.setOnClickListener {
            (activity as MainActivity).onChatButtonClicked()
        }

        newPost.setOnClickListener {
            (activity as MainActivity).onNewPostButtonClicked()
        }

        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}