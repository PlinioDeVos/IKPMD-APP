package nl.pdevos.ikpmd;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatabaseCommunicator {
    private FirebaseDatabase database;
    private DatabaseReference meetingsRef;

    public DatabaseCommunicator() {
        database = FirebaseDatabase.getInstance("https://ikpmd-dec9b-default-rtdb.europe-west1.firebasedatabase.app/");
        meetingsRef = database.getReference("users/" + User.getInstance().getUid() + "/meetings");
    }

    public void createMeeting(String name, String date) {
        String meeting_id = meetingsRef.push().getKey();
        meetingsRef.child(meeting_id + "/name").setValue(name);
        meetingsRef.child(meeting_id + "/date").setValue(date);
    }

    public ArrayList<Meeting> getMeetingsFromDate(String selectedDate) {
        ArrayList<Meeting> meetings = new ArrayList<>();

        meetingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Meeting meeting = getMeetingFromData(data);

                    if (meeting.getDate().equals(selectedDate))
                        meetings.add(meeting);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Firebase", error.getMessage());
            }
        });

        return meetings;
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

    public void updateMeetingFromId(String id) {

    }

    public void getMeetingFromId(String id) {

    }
}
