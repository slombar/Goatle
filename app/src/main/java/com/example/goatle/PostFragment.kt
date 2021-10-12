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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.random.Random.Default.nextInt

class PostFragment : Fragment() {


    interface Callbacks {
        //fun onReplySelected(postId: UUID)
        fun onExitSelected()
        fun onPostSelected()
    }
    private var callbacks: Callbacks? = null

    private lateinit var post: Post
    private lateinit var contentField: EditText
    private lateinit var username: TextView
    private lateinit var date: TextView
    private lateinit var postButton: Button
    private lateinit var exitButton: Button

    private  var KEY_Username : String = "postUsername"
    private  var KEY_Date : String = "postDate"
    private  var KEY_Content : String = "postContent"


    val names: List<String> = listOf("Happy_Hen", "Funny_Flamingo", "Tiny_Tiger")

    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())


    private var dbd : FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        post = Post()

    }

    //override fun onAttach(context: Context) {
    //    super.onAttach(context)
        //callbacks = context as Callbacks?
    //}

    private inner class PostHolder(view: View)
        :  View.OnClickListener {
        override fun onClick(v: View?) {
           // callbacks?.onReplySelected(post.id)
        }}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.post_fragment, container, false)
        contentField = view.findViewById(R.id.contentText) as EditText
        date = view.findViewById(R.id.dateView) as TextView
        username = view.findViewById(R.id.userName) as TextView
//        username = view.findViewById(R.id.postUsername) as TextView
        postButton = view.findViewById(R.id.postButton) as Button
        exitButton = view.findViewById(R.id.exitButton) as Button

        exitButton.setOnClickListener {
            (activity as MainActivity).onExitSelected()

        }
        postButton.setOnClickListener(){
            savePost()
            //contentField.text.clear()
            (activity as MainActivity).onPostSelected()

        }

        username.apply {
            text =  names.random()
            isEnabled = true
        }
        date.apply{
            text = currentDate
            isEnabled = true

        }

//        username.apply {
//            text = post.date.toString()
//            isEnabled = true
//        }


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




}