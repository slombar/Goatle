package com.example.goatle

import android.content.ClipData.newIntent
import android.content.Intent
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.util.Log

import androidx.fragment.app.Fragment
import sadieapi.ChatMainActivity
import java.util.*

private const val TAG = "HomeFragmentTag"
private const val REQUEST_CODE_CHAT = 0
private const val KEY_NICKNAME = "key_nickname_home"

class HomeFragment : Fragment() {

    interface Callbacks {
        fun onCreatePostSelected()
    }

    private var callbacks: Callbacks? = null
    private lateinit var createPostButton : ImageButton


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onStart() {
        super.onStart()
        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int ){
                // This space intentionally left blank
            }
            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int ){
                //crime.title = sequence.toString()
            }
            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }
        //titleField.addTextChangedListener(titleWatcher)


    }
    val username = "testUsername"
    private lateinit var chatHomeButton: ImageButton
    private lateinit var newPost: ImageButton



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

        createPostButton = view.findViewById(R.id.newPostButtonHomePage)

        createPostButton.setOnClickListener {
            (activity as MainActivity).onCreatePostSelected()
        }

        chatHomeButton = view.findViewById(R.id.chatButtonHomePage)

        chatHomeButton.setOnClickListener {
            val intent = ChatMainActivity.newIntent(activity as MainActivity)
            startActivityForResult(intent, REQUEST_CODE_CHAT)
        }

        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}