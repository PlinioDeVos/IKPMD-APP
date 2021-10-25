package nl.pdevos.ikpmd.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

import nl.pdevos.ikpmd.models.ColorConverter;
import nl.pdevos.ikpmd.models.Meeting;
import nl.pdevos.ikpmd.R;
import nl.pdevos.ikpmd.application.EvaluateActivity;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.MeetingViewHolder> {
    private final ArrayList<Meeting> meetings;
    private static Context context;

    public MeetingRecyclerViewAdapter(Context context, ArrayList<Meeting> meetings) {
        this.context = context;
        this.meetings = meetings;
    }

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_row_item, parent, false);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        Meeting meeting = meetings.get(position);
        holder.meeting = meeting;
        holder.itemName.setText(meeting.getName());
        holder.itemDate.setText(meeting.getDate());

        if (meeting.getColor() != null) {
            ColorConverter colorConverter = new ColorConverter(context.getResources());
            holder.itemColor.setText(meeting.getColor());
            holder.itemColor.setTextColor(colorConverter.getIntColorFromColorName(meeting.getColor()));
        }

    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public static class MeetingViewHolder extends RecyclerView.ViewHolder {
        private Meeting meeting;
        private final TextView itemName, itemDate, itemColor;

        public MeetingViewHolder(@NonNull View v) {
            super(v);

            itemName = v.findViewById(R.id.itemName);
            itemDate = v.findViewById(R.id.itemDate);
            itemColor = v.findViewById(R.id.itemColor);

            v.findViewById(R.id.itemButton).setOnClickListener(view -> {
                Intent intent = new Intent(context, EvaluateActivity.class);
                intent.putExtra("meeting", (Serializable) meeting);
                context.startActivity(intent);
            });
        }
    }
}
