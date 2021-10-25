package nl.pdevos.ikpmd.application;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import nl.pdevos.ikpmd.database.DatabaseCommunicator;
import nl.pdevos.ikpmd.models.DateCreator;
import nl.pdevos.ikpmd.R;

public class CreateActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private EditText nameEditText;
    private String selectedDate;
    private DateCreator dateCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        dateCreator = new DateCreator();

        nameEditText = (EditText) findViewById(R.id.name_edittext);
        calendarView = (CalendarView) findViewById(R.id.date_view);

        selectedDate = dateCreator.getStringFromDate(new Date(calendarView.getDate()));
        setupDateChangeListener();
    }

    private void setupDateChangeListener() {
        calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) ->
                selectedDate = dateCreator.getStringFromYearMonthDay(year, month + 1, dayOfMonth));
    }

    private void saveMeeting() {
        String name = nameEditText.getText().toString().trim();

        DatabaseCommunicator databaseCommunicator = new DatabaseCommunicator();
        databaseCommunicator.createMeeting(name, selectedDate);
    }

    public void deleteClicked(View v) {
        finish();
    }

    public void saveClicked(View v) {
        String name = nameEditText.getText().toString();

        if (name.trim().isEmpty()) {
            Toast.makeText(this, R.string.name_is_empty_msg, Toast.LENGTH_SHORT).show();
            return;
        }

        saveMeeting();
        finish();
    }
}