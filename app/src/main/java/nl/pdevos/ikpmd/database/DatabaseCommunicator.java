package nl.pdevos.ikpmd.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import nl.pdevos.ikpmd.models.Meeting;

public class DatabaseCommunicator {
    private final DatabaseReference meetingsRef;

    public DatabaseCommunicator() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ikpmd-dec9b-default-rtdb.europe-west1.firebasedatabase.app/");
        meetingsRef = database.getReference("users/" + User.getInstance().getUid() + "/meetings");
    }

    public void createMeeting(String name, String date) {
        String meeting_id = meetingsRef.push().getKey();
        meetingsRef.child(meeting_id + "/name").setValue(name);
        meetingsRef.child(meeting_id + "/date").setValue(date);
    }

    public void readMeetingData(FirebaseCallback firebaseCallback) {
        ArrayList<Meeting> meetings = new ArrayList<>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    meetings.add(getMeetingFromData(data));
                }

                firebaseCallback.onCallback(meetings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Firebase", error.getMessage());
            }
        };

        meetingsRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private Meeting getMeetingFromData(DataSnapshot data) {
        Meeting meeting = new Meeting();
        meeting.setId(data.getKey());
        meeting.setName(getStringValueFromChild(data, "name"));
        meeting.setDate(getStringValueFromChild(data, "date"));
        meeting.setColor(getStringValueFromChild(data, "color"));
        meeting.setCommentary(getStringValueFromChild(data, "commentary"));

        return meeting;
    }

    private String getStringValueFromChild(DataSnapshot data, String child) {
        return data.child(child).getValue(String.class);
    }

    public void updateMeetingFromId(String meeting_id, String color, String commentary) {
        if (color != null)
            meetingsRef.child(meeting_id + "/color").setValue(color);

        if (commentary != null)
            if (!commentary.trim().isEmpty())
                meetingsRef.child(meeting_id + "/commentary").setValue(commentary);
    }
}
