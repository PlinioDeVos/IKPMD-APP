package nl.pdevos.ikpmd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.MeetingViewHolder> {
    private ArrayList<Meeting> meetings;

    public MeetingRecyclerViewAdapter(ArrayList<Meeting> meetings) {
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
        holder.itemName.setText(meeting.getName());
        holder.itemDate.setText(meeting.getDate());
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public static class MeetingViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName;
        private TextView itemDate;

        public MeetingViewHolder(@NonNull View v) {
            super(v);

            itemName = v.findViewById(R.id.itemName);
            itemDate = v.findViewById(R.id.itemDate);
        }
    }
}
