package nl.pdevos.ikpmd.application;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import nl.pdevos.ikpmd.database.DatabaseCommunicator;
import nl.pdevos.ikpmd.models.DateCreator;
import nl.pdevos.ikpmd.models.Meeting;
import nl.pdevos.ikpmd.adapters.MeetingRecyclerViewAdapter;
import nl.pdevos.ikpmd.R;

public class MainActivity extends AppCompatActivity {
    private DatabaseCommunicator databaseCommunicator;

    private DatePickerDialog datePickerDialog;
    private Button date_picker;
    private DateCreator dateCreator;

    private RecyclerView recyclerView;
    private MeetingRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseCommunicator = new DatabaseCommunicator();

        setupDatePicker();
        setupRecyclerView();
    }

    private void setupDatePicker() {
        dateCreator = new DateCreator();
        date_picker = (Button) findViewById(R.id.date_picker);
        date_picker.setText(dateCreator.getCurrentDate());

        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, dayOfMonth) -> {
            String date = dateCreator.getStringFromYearMonthDay(year, month + 1, dayOfMonth);
            date_picker.setText(date);
            loadMeetingItems();
        };

        createDatePickerDialog(dateSetListener);
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void loadMeetingItems() {
        databaseCommunicator.readMeetingData(meetings -> {
            ArrayList<Meeting> results = new ArrayList<>();
            String selectedDate = (String) date_picker.getText();

            for (Meeting meeting : meetings) {
                if (meeting.getDate().equals(selectedDate))
                    results.add(meeting);
            }

            adapter = new MeetingRecyclerViewAdapter(this, results);
            recyclerView.setAdapter(adapter);
        });
    }

    private void createDatePickerDialog(DatePickerDialog.OnDateSetListener dateSetListener) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    public void addMeetingClicked(View v) {
        startActivity(new Intent(this, CreateActivity.class));
    }

    public void datePickerClicked(View v) {
        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        // Dit zorgt er voor dat de gebruiker niet terug kan met de back button
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadMeetingItems();
    }
}