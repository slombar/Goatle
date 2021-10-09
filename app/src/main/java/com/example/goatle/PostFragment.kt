package com.example.goatle


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.*

class PostFragment : Fragment() {


    interface Callbacks {
        fun onPostSelected(postId: UUID)
    }
    private var callbacks: Callbacks? = null

    private lateinit var post: Post
    private lateinit var contentField: EditText
    private lateinit var date: TextView
    private lateinit var postButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        post = Post()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    private inner class PostHolder(view: View)
        :  View.OnClickListener {
        override fun onClick(v: View?) {
            callbacks?.onPostSelected(post.id)
        }}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.post_fragment, container, false)
        contentField = view.findViewById(R.id.contentText) as EditText
        date = view.findViewById(R.id.dateView) as TextView
        postButton = view.findViewById(R.id.postButton) as Button

        date.apply {
            text = post.date.toString()
            isEnabled = true
        }

        return view }

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
                post.postContent = sequence.toString()
            }
            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }
        contentField.addTextChangedListener(titleWatcher)
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}