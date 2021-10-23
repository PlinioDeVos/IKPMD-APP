package nl.pdevos.ikpmd;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button date_picker;
    private DateCreator dateCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, GoogleSignInActivity.class));

        dateCreator = new DateCreator();
        date_picker = (Button) findViewById(R.id.date_picker);
        date_picker.setText(dateCreator.getCurrentDate());
        setupDatePicker();
    }

    private void setupDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, dayOfMonth) -> {
            String date = dateCreator.getStringFromYearMonthDay(year, month + 1, dayOfMonth);
            date_picker.setText(date);
        };

        createDatePickerDialog(dateSetListener);
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
}