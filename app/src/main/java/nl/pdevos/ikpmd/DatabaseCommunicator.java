package nl.pdevos.ikpmd;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseCommunicator {
    FirebaseDatabase database;
    DatabaseReference meetingRef;

    public DatabaseCommunicator() {
        database = FirebaseDatabase.getInstance("https://ikpmd-dec9b-default-rtdb.europe-west1.firebasedatabase.app/");
        meetingRef = database.getReference("users/" + User.getInstance().getUid() + "/meetings");
    }

    public void createMeeting(String name, String date) {
        String meeting_id = meetingRef.push().getKey();
        meetingRef.child(meeting_id + "/name").setValue(name);
        meetingRef.child(meeting_id + "/date").setValue(date);
    }
}
