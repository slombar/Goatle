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
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*

private const val TAG = "ReplyFragment"
private const val ARG_Post_ID = "reply_id"
class ReplyFragment() : Fragment() {


    interface Callbacks {
        fun onReplyPostSelected()

    }

    private var callbacks: ReplyFragment.Callbacks? = null
    private lateinit var reply: Reply
    private lateinit var replyButton: Button
    private var dbd : FirebaseFirestore = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total reply: ${replyListViewModel.replys.size}")
    }

    private val replyListViewModel: ReplyListViewModel by lazy {
        ViewModelProviders.of(this).get(ReplyListViewModel::class.java)
    }


    private lateinit var replyList: RecyclerView
    private var adapter: ReplyAdapter? = null

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
        val view = inflater.inflate(R.layout.reply_fragment, container, false)
        replyList =
            view.findViewById(R.id.replyList) as RecyclerView
        replyList.layoutManager = LinearLayoutManager(context)

        replyButton = view.findViewById(R.id.replyButton)
        replyButton.setOnClickListener {
            (activity as MainActivity).onReplyPostSelected()
        }

        val postId: String = arguments?.getSerializable(ARG_Post_ID) as String

        val query : Query = dbd.collection("Posts").document(postId).collection("Replys")
            //dbd.collection("Replys").whereEqualTo("DocumentID", postId)
        val options = FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java)
            .setLifecycleOwner(this).build()
        val adapter3 = object: FirestoreRecyclerAdapter<Post, ReplyViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ReplyViewHolder {
                val view = layoutInflater.inflate(R.layout.reply_layout, parent, false)
                return ReplyViewHolder(view)
            }

            override fun onBindViewHolder(holder: ReplyViewHolder, p1: Int, post: Post) {
                val usernameTextView: TextView = holder.itemView.findViewById(R.id.postUsername)
                val dateTextView: TextView = holder.itemView.findViewById(R.id.postDate)
                val contentTV : TextView = holder.itemView.findViewById(R.id.postContent)

                usernameTextView.text = post.username
                dateTextView.text = post.date
                contentTV.text = post.postContent
            }

        }
        replyList.adapter = adapter3
        replyList.layoutManager = LinearLayoutManager(context)
        //updateUI()
        return view }

    private fun updateUI() {
        val replys = replyListViewModel.replys
        adapter = ReplyAdapter(replys)
        replyList.adapter = adapter
    }

    class ReplyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){

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
        // contentField.addTextChangedListener(titleWatcher)
    }


    private inner class ReplyHolder(view: View)
        : RecyclerView.ViewHolder(view){

        private lateinit var reply: Reply

        val usernameTextView: TextView = itemView.findViewById(R.id.replyUsername)
        val dateTextView: TextView = itemView.findViewById(R.id.replyDate)
        val replyContentTV : TextView = itemView.findViewById(R.id.replyContent)




        fun bind(reply: Reply) {
            this.reply = reply
            usernameTextView.text = this.reply.username
            dateTextView.text = this.reply.date
            replyContentTV.text = this.reply.replyContent

        }



    }
    private inner class ReplyAdapter(var replys: List<Reply>)
        : RecyclerView.Adapter<ReplyHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : ReplyHolder {
            val view = layoutInflater.inflate(R.layout.reply_layout, parent, false)
            return ReplyHolder(view)
        }
        override fun getItemCount() = replys.size

        override fun onBindViewHolder(holder: ReplyHolder, position: Int) {
            val reply = replys[position]
            if(position %2 == 1) {
                holder?.itemView.setBackgroundColor(Color.parseColor("#5F8575"))}
            else{
                holder?.itemView.setBackgroundColor(Color.parseColor("#228B22"))
            }
            holder.bind(reply)
        } }
}