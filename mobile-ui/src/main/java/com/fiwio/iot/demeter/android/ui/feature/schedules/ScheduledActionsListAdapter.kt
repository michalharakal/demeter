package com.fiwio.iot.demeter.android.ui.feature.manual

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.fiwio.iot.demeter.android.ui.R
import com.fiwio.iot.demeter.presentation.model.ScheduledActionModel
import com.fiwio.iot.demeter.presentation.model.ScheduledActionsModel


internal class ScheduledActionsListAdapter(private val listener: OnItemClickListener) :
        RecyclerView.Adapter<ScheduledActionsListAdapter.ViewHolder>() {

    var data: ScheduledActionsModel = ScheduledActionsModel(emptyList())


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.click(data.actions.get(position), listener)
        holder.name.text = data.actions.get(position).name
        holder.branch.text = data.actions.get(position).branch + ", " + data.actions.get(position).command
        holder.time.text = data.actions.get(position).dayTime
        holder.background.setBackgroundResource(if (data.actions.get(position).isActiveNow)
            R.drawable.led_circle_green
        else
            R.drawable.led_circle_grey)
    }

    override fun getItemCount(): Int {
        return data.actions.size
    }

    interface OnItemClickListener {
        fun onClick(actuator: ScheduledActionModel)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var name: TextView
        internal var branch: TextView
        internal var time: TextView
        internal var background: ImageView

        init {
            name = itemView.findViewById(R.id.action_name) as TextView
            branch = itemView.findViewById(R.id.action_branch) as TextView
            time = itemView.findViewById(R.id.action_time) as TextView
            background = itemView.findViewById(R.id.led) as ImageView

        }

        fun click(actuator: ScheduledActionModel, listener: OnItemClickListener) {
            itemView.setOnClickListener { listener.onClick(actuator) }
        }
    }
}