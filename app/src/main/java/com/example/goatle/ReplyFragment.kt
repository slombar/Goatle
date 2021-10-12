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


    interface Callbacks {
        fun onReplyPostSelected()

    }

    private var callbacks: ReplyFragment.Callbacks? = null
    private lateinit var reply: Reply
    private lateinit var replyButton: Button
    private lateinit var postUsername: TextView
    private lateinit var postDate: TextView
    private lateinit var postContent: TextView
    private var dbd : FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total reply: ${replyListViewModel.replys.size}")
    }

    private val replyListViewModel: ReplyListViewModel by lazy {
        ViewModelProviders.of(this).get(ReplyListViewModel::class.java)
    }

    private lateinit var replyList: RecyclerView
   // private var adapter: ReplyAdapter? = null

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
            view.findViewById(R.id.replyList2) as RecyclerView
        //replyList.layoutManager = LinearLayoutManager(context)

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

        dbd.collection("Posts").whereEqualTo("DocumentID", postId)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    postUsername.text = document.get("postUsername").toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
        replyButton = view.findViewById(R.id.makeReply)
        replyButton.setOnClickListener {
            (activity as MainActivity).onReplyPostSelected()
        }
        replyList.adapter = adapter3
        replyList.layoutManager = LinearLayoutManager(context)
        //updateUI()
        return view }

    private fun updateUI() {
//        val replys = replyListViewModel.replys
//        adapter = ReplyAdapter(replys)
//        replyList.adapter = adapter
    }

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
            usernameTextView.text = this.reply.replyUsername
            dateTextView.text = this.reply.replyDate
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