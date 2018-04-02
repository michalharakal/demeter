package com.fiwio.iot.demeter.android.ui.feature.manual

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.fiwio.iot.demeter.android.ui.R
import com.fiwio.iot.demeter.domain.model.Relay
import com.fiwio.iot.demeter.presentation.model.ActuatorState
import com.fiwio.iot.demeter.presentation.model.ActuatorView


internal class ActuatorsListAdapter :
        RecyclerView.Adapter<ActuatorsListAdapter.ViewHolder>() {

    var data: List<ActuatorView> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_actuator, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.click(data.getRelays().get(position), listener)
        holder.name.setText(data.get(position).caption)
        holder.value.setText(data.get(position).state.toString())
        holder.background.setBackgroundResource(if (data.get(position).state.equals(ActuatorState.ON))
            R.drawable.led_circle_green
        else
            R.drawable.led_circle_grey)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface OnItemClickListener {
        fun onClick(Item: Relay)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var name: TextView
        internal var value: TextView
        internal var background: ImageView

        init {
            name = itemView.findViewById(R.id.releay_name) as TextView
            value = itemView.findViewById(R.id.relay_value) as TextView
            background = itemView.findViewById(R.id.led) as ImageView

        }
    }
}