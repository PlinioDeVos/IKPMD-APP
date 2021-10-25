package nl.pdevos.ikpmd.database;

import java.util.ArrayList;

import nl.pdevos.ikpmd.models.Meeting;

public interface FirebaseCallback {
    void onCallback(ArrayList<Meeting> meetings);
}
