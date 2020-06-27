package com.fiwio.iot.demeter.android.ui.feature.messages

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.fiwio.iot.demeter.android.ui.R
import com.fiwio.iot.demeter.android.ui.feature.manual.ScheduledActionsListAdapter
import com.fiwio.iot.demeter.presentation.model.MessagesModel
import com.fiwio.iot.demeter.presentation.model.ScheduledActionModel

internal class MessagesListAdapter(private val iconsMapper: IconsMapper) :
        RecyclerView.Adapter<MessagesListAdapter.ViewHolder>() {
    lateinit var data: MessagesModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.messages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = data.messages.get(position).dateTime
        holder.branch.text = data.messages.get(position).message
        holder.background.setBackgroundResource(iconsMapper.getIconFromName(data.messages.get(position).message))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var title: TextView = itemView.findViewById(R.id.message_title) as TextView
        internal var branch: TextView = itemView.findViewById(R.id.message_body) as TextView
        internal var time: TextView = itemView.findViewById(R.id.message_time) as TextView
        internal var background: ImageView = itemView.findViewById(R.id.message_symbol) as ImageView

        fun click(actuator: ScheduledActionModel, listener: ScheduledActionsListAdapter.OnItemClickListener) {
            itemView.setOnClickListener { listener.onClick(actuator) }
        }
    }
}
