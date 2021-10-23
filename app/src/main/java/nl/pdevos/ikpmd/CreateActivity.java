package nl.pdevos.ikpmd;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private EditText nameEditText;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        nameEditText = (EditText) findViewById(R.id.name_edittext);

        calendarView = (CalendarView) findViewById(R.id.date_view);
        selectedDate = getStringFromDate(new Date(calendarView.getDate()));
        setupDateChangeListener();
    }

    private void setupDateChangeListener() {
        calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) ->
                selectedDate = getStringFromYearMonthDay(year, month, dayOfMonth));
    }

    private String getStringFromDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    private String getStringFromYearMonthDay(int year, int month, int dayOfMonth) {
        return String.format("%d/%d/%d", dayOfMonth, month, year);
    }

    private void saveMeeting() {
        String name = nameEditText.getText().toString().trim();

        DatabaseCommunicator dbCommunicator = new DatabaseCommunicator();
        dbCommunicator.createMeeting(name, selectedDate);
    }

    public void deleteClicked(View v) {
        finish();
    }

    public void saveClicked(View v) {
        String name = nameEditText.getText().toString();

        if (name.trim().isEmpty()) {
            Toast.makeText(CreateActivity.this, R.string.name_is_empty_msg, Toast.LENGTH_SHORT).show();
            return;
        }

        saveMeeting();
        finish();
    }
}