package com.fiwio.iot.demeter.scheduler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fiwo.iot.demeter.api.model.ScheduledEvent;
import com.fiwo.iot.demeter.api.model.ScheduledEvents;
import com.fiwo.iot.demeter.smart.R;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

class SchedulerListAdapter extends RecyclerView.Adapter<SchedulerListAdapter.ViewHolder> {
    private final SchedulerListAdapter.OnItemClickListener listener;
    private ScheduledEvents events;
    private Context context;

    public SchedulerListAdapter(Context context, ScheduledEvents events, SchedulerListAdapter.OnItemClickListener listener) {
        this.events = events;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public SchedulerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new SchedulerListAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SchedulerListAdapter.ViewHolder holder, int position) {
        holder.click(events.get(position), listener);
        holder.name.setText(events.get(position).getCommand());

        DateTimeZone timeZone = DateTimeZone.forID("Europe/Berlin");

        DateTimeFormatter formatter = DateTimeFormat.mediumDateTime().withLocale(Locale.getDefault()).withZone
                (timeZone);
        holder.value.setText(formatter.print(events.get(position).getTime()));
    }


    @Override
    public int getItemCount() {
        return events.size();
    }


    public interface OnItemClickListener {
        void onClick(ScheduledEvent Item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, value;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.releay_name);
            value = (TextView) itemView.findViewById(R.id.relay_value);
        }


        public void click(final ScheduledEvent event, final SchedulerListAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(event);
                }
            });
        }
    }


}
