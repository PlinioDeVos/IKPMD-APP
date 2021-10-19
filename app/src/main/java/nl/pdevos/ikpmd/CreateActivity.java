package nl.pdevos.ikpmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
    }

    public void deleteClicked(View v) {
        finish();
//        startActivity(new Intent(this, MainActivity.class));
    }

    public void saveClicked(View v) {
        finish();
//        startActivity(new Intent(this, MainActivity.class));
    }
}