package com.example.goatle

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import java.util.*

private const val TAG = "ReplyFragment"
private const val ARG_CRIME_ID = "reply_id"
class ReplyFragment : Fragment() {

    private lateinit var reply: Reply
    private lateinit var replyButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reply = Reply()
    }
//    private val replyListViewModel: ReplyListViewModel by lazy {
//        ViewModelProviders.of(this).get(ReplyListViewModel::class.java)
//    }



    companion object {
        fun newInstance(replyId: UUID): ReplyFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CRIME_ID, replyId)
            }
            return ReplyFragment().apply {
                arguments = args
            }
        } }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.reply_fragment, container, false)

        replyButton = view.findViewById(R.id.replyButton) as Button

        replyButton.apply {
            text = "Reply"
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
                //post.postContent = sequence.toString()
            }
            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }
        // contentField.addTextChangedListener(titleWatcher)
    }
}