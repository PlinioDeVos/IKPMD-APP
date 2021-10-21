package nl.pdevos.ikpmd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, GoogleSignInActivity.class));

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ikpmd-dec9b-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference userRef = database.getReference("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        userRef.child("message1").setValue("Hello World");
    }

    public void meetingClicked(View v) {
        startActivity(new Intent(this, CreateActivity.class));
    }
}