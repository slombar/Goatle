package sadieapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class StatusAdapter : RecyclerView.Adapter<StatusAdapter.ViewHolder>() {

    private var statusList = ArrayList<String>()
    private var reversedList = ArrayList<String>()

    fun addMessage(newMessage: String){
        statusList.add(newMessage)
        reversedList = statusList
        reversedList.reverse()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return statusList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.statusText.text = reversedList[position]
    }

    inner class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        var statusText: TextView = itemView!!.findViewById(android.R.id.text1)
    }

}