package com.example.goatle


import android.content.Context

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList
import androidx.annotation.NonNull
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import sadieapi.ChatMainActivity

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.*


private const val TAG = "HomeFragmentTag"
private const val REQUEST_CODE_CHAT = 0
private const val KEY_NICKNAME = "key_nickname_home"

class HomeFragment : Fragment() {

    val hex1 = "#d3d3ff"
    val hex2 = "#d393ff"
    val hex3 = "#de66dc"
    val hex4 = "#de6672"

    var backgroundColor1 = hex1
    var backgroundColor2 = hex2


    interface Callbacks {
        fun onCreatePostSelected()
        fun onReplyClicked(id: String)

    }

    private val postListViewModel: PostListViewModel by lazy {
        ViewModelProviders.of(this).get(PostListViewModel::class.java)

    }
    private var callbacks: Callbacks? = null
    private lateinit var createPostButton: ImageButton

    private var dbd: FirebaseFirestore = FirebaseFirestore.getInstance()


    private lateinit var postList: RecyclerView
    //private var adapter: PostAdapter? = null


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
                after: Int
            ) {
                // This space intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
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
        //outState.putString(KEY_NICKNAME, username)

    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //removes the overlapping problem
        container?.removeAllViews()


        val view = inflater.inflate(R.layout.home_bar, container, false)
        postList = view.findViewById(R.id.postList) as RecyclerView
        //postList.layoutManager = LinearLayoutManager(context)


        val query: CollectionReference = dbd.collection("Posts")
        val options = FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java)
            .setLifecycleOwner(this).build()
        val adapter3 = object : FirestoreRecyclerAdapter<Post, PostViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
                val view = layoutInflater.inflate(R.layout.post_layout, parent, false)

                return PostViewHolder(view)
            }

            override fun onBindViewHolder(holder: PostViewHolder, p1: Int, post: Post) {
//                val usernameTextView: TextView = holder.itemView.findViewById(R.id.postUsername)
//                val dateTextView: TextView = holder.itemView.findViewById(R.id.postDate)
//                val contentTV : TextView = holder.itemView.findViewById(R.id.postContent)
//
//                usernameTextView.text = post.username
//                dateTextView.text = post.date
//                contentTV.text = post.postContent
                var documentId = snapshots.getSnapshot(p1).id
                Log.d(TAG, "Total crimsdfdfes: ${documentId}")


                if (p1 % 2 == 1) {
                    holder?.itemView.setBackgroundColor(Color.parseColor(backgroundColor1))
                } else {
                    holder?.itemView.setBackgroundColor(Color.parseColor(backgroundColor2))
                }


                holder.bind(post, documentId)

            }

        }
        postList.adapter = adapter3
        postList.layoutManager = LinearLayoutManager(context)

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

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private lateinit var post: Post
        val usernameTextView: TextView = itemView.findViewById(R.id.postUsername)
        val dateTextView: TextView = itemView.findViewById(R.id.postDate)
        val contentTV : TextView = itemView.findViewById(R.id.postContent)
        lateinit var doc :String


        init {
            itemView.setOnClickListener(this)

        }
        fun bind(post: Post, dId : String) {
             doc = dId
            this.post = post
            usernameTextView.text = this.post.postUsername
            dateTextView.text = this.post.postDate
            contentTV.text = this.post.postContent

        }

        override fun onClick(p0: View?) {

            (activity as MainActivity).onReplyClicked(doc)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    //TODO COLOR CHANGING

    fun changeColors(shook : Boolean){
        if(shook) {
            backgroundColor1 = hex3
            backgroundColor2 = hex4
        }else{
            backgroundColor1 = hex1
            backgroundColor2 = hex2
        }
    }
}