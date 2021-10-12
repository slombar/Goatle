//package sadieapi
//
//import android.app.Activity
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import com.example.goatle.R
//import android.view.View
//import android.view.inputmethod.InputMethodManager
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.goatle.MainActivity
//import com.pusher.client.Pusher
//import com.pusher.client.PusherOptions
//import com.pusher.client.channel.ChannelEventListener
//import com.pusher.client.channel.PusherEvent
//import org.json.JSONObject
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//const val PUSHER_API_KEY = "b65f2d2ca63e8f87d4e8"
//const val PUSHER_CLUSTER = "mt1"
//const val channelName = "Goatle"
//const val eventName = "my-event"
//const val TAG ="ChatMainActivity"
//const val getStringName = "message"
//const val KEY = "Chatmainactivity_key"
//
//
//class ChatMainActivity : AppCompatActivity() {
//
//    lateinit var pusher:Pusher
//    lateinit var buttonPost:Button
//    lateinit var newStatus:EditText
//    lateinit var recyclerView:RecyclerView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.chat_main_activity)
//
//        Log.d(TAG, "startin")
//
//        //initialize variables because the code doesn't on the website
//        buttonPost = findViewById(R.id.buttonPost)
//        newStatus = findViewById(R.id.newStatus)
//        recyclerView = findViewById(R.id.chatRecyclerView)
//
//
//        // setup recycler view and adapter
//        val adapter = StatusAdapter()
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapter
//
//        // setup pusher to receive status update
//        val options = PusherOptions()
//        options.setCluster(PUSHER_CLUSTER)
//        pusher = Pusher(PUSHER_API_KEY, options)
//        val channel = pusher.subscribe(channelName)
//
//
//        channel.bind(eventName, object: ChannelEventListener {
//
//            override fun onEvent(event: PusherEvent?) {
//
//                if (event != null) {
//
//                    val jsonObject = JSONObject(event.data)
//
//                    Log.d(TAG, jsonObject.toString())
//
//                    runOnUiThread {
//                        Log.d(TAG, jsonObject.getString(getStringName))
//                        adapter.addMessage(jsonObject.getString(getStringName))
//                    }
//                }
//            }
//
//            override fun onSubscriptionSucceeded(channelName: String?) {
//                Log.d(TAG, "succ sess")
//            }
//        })
//
//        // post status to server
//        buttonPost.setOnClickListener {
//
//            if (newStatus.text.isNotEmpty())
//                RetrofitClient().getClient().updateStatus(newStatus.text.toString()).enqueue(object : Callback<String> {
//                    override fun onResponse(call: Call<String>?, response: Response<String>?) {
//                        Log.d(TAG, "User entered: " + newStatus.text.toString())
//                        newStatus.text.clear()
//                        hideKeyboard()
//
//                    }
//
//                    override fun onFailure(call: Call<String>?, t: Throwable?) {
//                        Toast.makeText(this@ChatMainActivity,"Error occurred",Toast.LENGTH_SHORT).show()
//                    }
//                })
//        }
//    }
//
//    private fun hideKeyboard() {
//        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//        var view = this.currentFocus
//        if (view == null)
//            view = View(this)
//        imm.hideSoftInputFromWindow(view.windowToken, 0)
//    }
//
//    companion object{
//        fun newIntent(packageContext: MainActivity): Intent {
//            return Intent(packageContext, ChatMainActivity::class.java)
//                .apply{
//                    putExtra(KEY, true)
//                }
//        }
//    }
//
//
//    override fun onResume() {
//        super.onResume()
//        pusher.connect()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        pusher.disconnect()
//    }
//}