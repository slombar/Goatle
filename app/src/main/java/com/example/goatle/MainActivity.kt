package com.example.goatle

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Connection
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.sqrt

private const val TAG = "MainActivityTag"


class MainActivity : AppCompatActivity(),  HomeFragment.Callbacks, PostFragment.Callbacks, ReplyFragment.Callbacks {



    var shook = false
    private  var ip : String = ""
    private var port : String = "1433"
    private var Class: String = "net.sourceforge.jtds.jdbc.Driver"
    private var database : String = "Content"
    private var username : String = "sa"
    private var password : String = "Mobile2021"
    private var url : String = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database

    private var connection: Connection? = null

    //accelerometer sensor
    private var sensorManager: SensorManager? = null
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f
    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcceleration = currentAcceleration
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta
            if (acceleration > 10) {
                Toast.makeText(applicationContext, "Woah!", Toast.LENGTH_SHORT).show()
                onDeviceShook()

            }
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    override fun onResume() {
        sensorManager?.registerListener(sensorListener, sensorManager!!.getDefaultSensor(
            Sensor .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
        )
        super.onResume()
    }
    override fun onPause() {
        sensorManager!!.unregisterListener(sensorListener)
        super.onPause()
    }

    //end accelerometer sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //sensor
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        Objects.requireNonNull(sensorManager)!!.registerListener(sensorListener, sensorManager!!
            .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        acceleration = 10f
        currentAcceleration = SensorManager.GRAVITY_EARTH
        lastAcceleration = SensorManager.GRAVITY_EARTH

        //end sensor
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            Log.d(TAG, "The current fragment is null idiot moron")
            val fragment = HomeFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()

        }

        //set up reclyviewer
    }

    //TODO CHANGE COLORS
    fun onDeviceShook(){
        //first shake = true

            val fragment = HomeFragment.newInstance()

            fragment.changeColors(shook)

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()

        shook = !shook;

    }

    override fun onCreatePostSelected() {
        val fragment = PostFragment.newInstance(false,"none")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onExitSelected() {
        val fragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun onHomeButtonClicked(){
        val fragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }

    fun addRecord(view: View, usernameText: String, postText: String, contentText: String){
        val username = usernameText
        val date = postText
        val content = contentText
        val dataBaseHandler : DatabaseHandler = DatabaseHandler(this)
        if(!username.isEmpty() && !date.isEmpty() && !content.isEmpty()){
            val status = dataBaseHandler.addPost(Post(0, username, date, content))
            if(status > -1){
                Toast.makeText(applicationContext, "Record Saved", Toast.LENGTH_LONG)

                //missing a function dealing with reclye viewer
            }
        }
        else{
            Toast.makeText(applicationContext, "Cannot be blank", Toast.LENGTH_LONG).show()
        }

    }
    override fun onPostSelected() {
        val fragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }


    override fun onReplyClicked(id: String) {
        val fragment = ReplyFragment.newInstance(id)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onReplyPostSelected(isReply : Boolean, postId: String){
        val fragment = PostFragment.newInstance(isReply, postId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

    }
    override fun replyToHome() {
        val fragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
    override fun replyCreated(id: String) {
        val fragment = ReplyFragment.newInstance(id)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }



}