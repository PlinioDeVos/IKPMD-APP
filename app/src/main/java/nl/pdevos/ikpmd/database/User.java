package nl.pdevos.ikpmd.database;

import com.google.firebase.auth.FirebaseUser;

public class User {
    private static User user;
    private static FirebaseUser firebaseUser;

    private User() {}

    public static User getInstance() {
        if (user == null)
            user = new User();

        return user;
    }

    public void setUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    public FirebaseUser getUser() {
        return firebaseUser;
    }

    public String getUid() {
        return firebaseUser.getUid();
    }
}
