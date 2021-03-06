package com.example.goatle

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*

private const val TAG = "ReplyFragment"
private const val ARG_Post_ID = "DocumentID"
class ReplyFragment() : Fragment() {

    val hex1 = "#074650"
    val hex2 = "#009292"
    val hex3 = "#fe6db6"
    val hex4 = "#feb5da"

    var backgroundColor1 = hex1
    var backgroundColor2 = hex2

    interface Callbacks {
        fun onReplyPostSelected(isReply : Boolean, postID : String)
        fun replyToHome()
    }

    private var callbacks: ReplyFragment.Callbacks? = null
    private lateinit var reply: Reply
    private lateinit var replyButton: Button
    private lateinit var exitButton: Button
    private lateinit var postUsername: TextView
    private lateinit var postDate: TextView
    private lateinit var postContent: TextView

    private var dbd : FirebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var replyList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total reply: ${replyListViewModel.replys.size}")
    }

    private val replyListViewModel: ReplyListViewModel by lazy {
        ViewModelProviders.of(this).get(ReplyListViewModel::class.java)
    }


    companion object {
        fun newInstance(id:String): ReplyFragment {
            val args = Bundle().apply {
                putSerializable(ARG_Post_ID, id)
            }
            return ReplyFragment().apply {
                arguments = args
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        val view = inflater.inflate(R.layout.reply_fragment2, container, false)
        replyList =
            view.findViewById(R.id.postList) as RecyclerView


        val postId: String = arguments?.getSerializable(ARG_Post_ID) as String
        Log.d(TAG, "PostID: ${postId}")

        val query : Query = dbd.collection("Posts").document(postId).collection("Replys")
            //dbd.collection("Replys").whereEqualTo("DocumentID", postId)
        val options = FirestoreRecyclerOptions.Builder<Reply>().setQuery(query, Reply::class.java)
            .setLifecycleOwner(this).build()
        val adapter3 = object: FirestoreRecyclerAdapter<Reply, ReplyViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ReplyViewHolder {
                val view = layoutInflater.inflate(R.layout.reply_layout2, parent, false)
                return ReplyViewHolder(view)
            }

            override fun onBindViewHolder(holder: ReplyViewHolder, p1: Int, reply: Reply) {
                holder.bind(reply)

            }

        }

        postUsername = view.findViewById(R.id.selectedPostUsername) as TextView
        postDate = view.findViewById(R.id.selectedPostDate) as TextView
        postContent = view.findViewById(R.id.selectedPostContent) as TextView

        val usersRef = dbd.collection("Posts")
        val uidRef = usersRef.document(postId)
        uidRef.get().addOnSuccessListener { document ->
            if (document != null) {
                postUsername.setText(document.getString("postUsername"))
                postDate.setText(document.getString("postDate"))
                postContent.setText(document.getString("postContent"))
            } else {
                Log.d(TAG, "No such document")
            }
        }.addOnFailureListener { exception ->
            Log.d(TAG, "get failed with ", exception)
        }


        replyButton = view.findViewById(R.id.makeReply2)
        replyButton.setOnClickListener {
            (activity as MainActivity).onReplyPostSelected(true, postId)
        }

        exitButton = view.findViewById(R.id.exit)
        exitButton.setOnClickListener {
            (activity as MainActivity).replyToHome()
        }
        replyList.adapter = adapter3
        replyList.layoutManager = LinearLayoutManager(context)

        return view }


    class ReplyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        private lateinit var reply: Reply
        val usernameTextView: TextView = itemView.findViewById(R.id.replyUsername2)
        val dateTextView: TextView = itemView.findViewById(R.id.replyDate2)
        val contentTV : TextView = itemView.findViewById(R.id.replyContent2)


        fun bind(reply: Reply) {
            this.reply = reply
            usernameTextView.text = reply.replyUsername
            dateTextView.text = reply.replyDate
            contentTV.text = reply.replyContent

            Log.d(TAG, "Username: ${reply.replyUsername}")
            Log.d(TAG, "Date: ${reply.replyDate}")
            Log.d(TAG, "Content: ${reply.replyContent}")

        }
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
                //post.postContent = sequence.toString()
            }
            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }
    }

}