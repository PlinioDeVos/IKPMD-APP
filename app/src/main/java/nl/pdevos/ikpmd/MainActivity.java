package nl.pdevos.ikpmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void meetingClicked(View v) {
        Log.d("Bijeenkomst", "Aanmaken");
        startActivity(new Intent(this, CreateActivity.class));
    }
}