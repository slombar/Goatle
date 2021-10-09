package com.example.goatle
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment

private const val TAG = "ChatFragmentTag"
private  val KEY_NICKNAME = "nickname_of_user"


class ChatFragment : Fragment() {
    val username = "testUsername"
    private lateinit var homeButton: ImageButton

    interface Callbacks {
        //get only one team's games
        fun onHomeButtonClicked()
    }


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

        //removes the overlapping problem
        container?.removeAllViews()

        val view = inflater.inflate(R.layout.chat_bar, container, false)
        //TODO assign buttons

        homeButton = view.findViewById(R.id.homeButton)

        homeButton.setOnClickListener {
            (activity as MainActivity).onHomeButtonClicked()

        }

        return view

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}