package com.example.goatle


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.random.Random.Default.nextInt

private const val ARG_IsReply = "True"
private const val ARG_Post_ID = "DocumentID"

private const val TAG = "PostFragment"
class PostFragment : Fragment() {


    interface Callbacks {
        fun onExitSelected()
        fun replyCreated(id: String)
        fun onPostSelected()
    }
    private var callbacks: Callbacks? = null

    private lateinit var post: Post
    private lateinit var contentField: EditText
    private lateinit var username: TextView
    private lateinit var title: TextView
    private lateinit var date: TextView
    private lateinit var postButton: Button
    private lateinit var exitButton: Button

    private  var KEY_Username : String = "postUsername"
    private  var KEY_Date : String = "postDate"
    private  var KEY_Content : String = "postContent"

    private  var KEY_UsernameR : String = "replyUsername"
    private  var KEY_DateR : String = "replyDate"
    private  var KEY_ContentR : String = "replyContent"

    val names: List<String> = listOf("Happy_Hen", "Funny_Flamingo", "Tiny_Tiger", "Red_Robin")

    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())

    private var dbd : FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        post = Post()
    }

    companion object {
        fun newInstance(isReply : Boolean, postId: String): PostFragment {
            val args = Bundle().apply {
                putSerializable(ARG_IsReply, isReply.toString())//true when comes from reply
                putSerializable(ARG_Post_ID, postId)
            }
            return PostFragment().apply {
                arguments = args
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.post_fragment, container, false)
        //setting the fields
        contentField = view.findViewById(R.id.contentText) as EditText
        date = view.findViewById(R.id.dateView) as TextView
        username = view.findViewById(R.id.userName) as TextView
        postButton = view.findViewById(R.id.postButton) as Button
        exitButton = view.findViewById(R.id.exitButton) as Button
        title = view.findViewById(R.id.postTitle) as TextView


        val postId2: String = arguments?.getSerializable(ARG_Post_ID) as String
        if(postId2 != "none"){
            title.setText("Create a Reply")
        }else if(postId2 == "none"){
            title.setText("Create a Post")

        }

        postButton.setOnClickListener(){
            if(postId2 != "none"){
                savePostReply()

                //contentField.text.clear()
                (activity as MainActivity).replyCreated(postId2)
            }else if(postId2 == "none"){
                savePost()
                (activity as MainActivity).onPostSelected()
            }
        }

        exitButton.setOnClickListener {
            (activity as MainActivity).onExitSelected()

        }

        //setting random username
        username.apply {
            text =  names.random()
            isEnabled = true
        }
        //setting current date
        date.apply{
            text = currentDate
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

    fun savePost(){
       var username2 : String   = username.text.toString()
       var date2 : String   = date.text.toString()
       var postC : String   = contentField.text.toString()


        val post = HashMap<String,Any>()
        post.put(KEY_Username, username2)
        post.put(KEY_Date, date2)
        post.put(KEY_Content, postC)

        dbd.collection("Posts")
            .add(post)
            .addOnSuccessListener {
                //Toast.makeText(context,"Data added ",Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
               // Toast.makeText(context," Data not added ",Toast.LENGTH_LONG).show()
            }



    }
    fun savePostReply() {
        var username2: String = username.text.toString()
        var date2: String = date.text.toString()
        var postC: String = contentField.text.toString()


        val reply = HashMap<String, Any>()
        reply.put(KEY_UsernameR, username2)
        reply.put(KEY_DateR, date2)
        reply.put(KEY_ContentR, postC)

        val postId: String = arguments?.getSerializable(ARG_Post_ID) as String
        Log.d(TAG, "PostID2: ${postId}")
        dbd.collection("Posts").document(postId).collection("Replys")
            .add(reply)
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }




}