package com.fiwio.iot.demeter.remote;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fiwo.iot.demeter.api.model.Demeter;
import com.fiwo.iot.demeter.api.model.Relay;
import com.fiwo.iot.demeter.smart.R;


public class RemoteControlListAdapter extends RecyclerView.Adapter<RemoteControlListAdapter
        .ViewHolder> {
    private final OnItemClickListener listener;
    private Demeter data;
    private Context context;

    public RemoteControlListAdapter(Context context, Demeter data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
        this.context = context;
    }

    private String getUserName(String internalName) {
        if (internalName.equals("BCM23")) {
            return "Napouštení";
        }
        if (internalName.equals("BCM24")) {
            return "Vypouštení";
        }
        if (internalName.equals("BCM22")) {
            return "Kytky";
        }
        return internalName;
    }


    @Override
    public RemoteControlListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new RemoteControlListAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.click(data.getRelays().get(position), listener);
        holder.name.setText(getUserName(data.getRelays().get(position).getName()));
        holder.value.setText(data.getRelays().get(position).getValue());
        holder.background.setBackgroundResource(data.getRelays().get(position).getValue().equals
                ("ON") ? R.drawable.led_circle_green : R.drawable.led_circle_grey);
    }


    @Override
    public int getItemCount() {
        return data.getRelays().size();
    }


    public interface OnItemClickListener {
        void onClick(Relay Item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, value;
        ImageView background;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.releay_name);
            value = (TextView) itemView.findViewById(R.id.relay_value);
            background = (ImageView) itemView.findViewById(R.id.led);

        }


        public void click(final Relay cityListData, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(cityListData);
                }
            });
        }
    }


}

