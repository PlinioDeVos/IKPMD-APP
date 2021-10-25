package nl.pdevos.ikpmd.application;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import nl.pdevos.ikpmd.models.ColorConverter;
import nl.pdevos.ikpmd.database.DatabaseCommunicator;
import nl.pdevos.ikpmd.models.Meeting;
import nl.pdevos.ikpmd.R;

public class EvaluateActivity extends AppCompatActivity {
    private DatabaseCommunicator databaseCommunicator;
    private Meeting meeting;

    private TextView colorView, nameView;
    private EditText commentaryEditText;

    private boolean alterationMade = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);

        databaseCommunicator = new DatabaseCommunicator();
        meeting = (Meeting) getIntent().getSerializableExtra("meeting");

        nameView = (TextView) findViewById(R.id.name_view);
        commentaryEditText = (EditText) findViewById(R.id.commentary_edittext);
        colorView = (TextView) findViewById(R.id.color_view);

        createTextChangedListener();
        loadMeetingValues();
    }

    private void loadMeetingValues() {
        if (meeting.getName() != null)
            nameView.setText(meeting.getName());

        if (meeting.getColor() != null)
            changeChosenColor(meeting.getColor());

        if (meeting.getCommentary() != null)
            commentaryEditText.setText(meeting.getCommentary());
    }

    private void createTextChangedListener() {
        commentaryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String newCommentary = charSequence.toString();
                meeting.setCommentary(newCommentary);
                alterationMade = true;
            }
        });
    }

    private void saveEvaluation() {
        databaseCommunicator.updateMeetingFromId(meeting.getId(),
                meeting.getColor(), meeting.getCommentary());

        finish();
    }

    private void changeChosenColor(String colorName) {
        ColorConverter colorConverter = new ColorConverter(getResources());

        colorView.setText(colorName);
        colorView.setTextColor(colorConverter.getIntColorFromColorName(colorName));
        meeting.setColor(colorName);
    }

    private boolean userMadeAlteration() {
        if (!alterationMade) {
            Toast.makeText(this, R.string.no_alteration, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void deleteClicked(View v) {
        finish();
    }

    public void saveClicked(View v) {
        if (userMadeAlteration())
            saveEvaluation();
    }

    public void colorClicked(View v) {
        alterationMade = true;

        switch (v.getId()) {
            case (R.id.red_button):
                changeChosenColor("Rood");
                break;
            case (R.id.yellow_button):
                changeChosenColor("Geel");
                break;
            case (R.id.green_button):
                changeChosenColor("Groen");
                break;
            default:
                Log.w("colorClicked", "Please add the new id to colorClicked!");
                break;
        }
    }
}