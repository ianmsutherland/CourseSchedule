package com.ianmsutherland.ianmsutherlandscheduler.UI.Assessment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.ianmsutherland.ianmsutherlandscheduler.Database.Repository;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Assessment;
import com.ianmsutherland.ianmsutherlandscheduler.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AssessmentAddActivity extends AppCompatActivity {

    final Calendar assessmentStartCalendar = Calendar.getInstance();
    final Calendar assessmentEndCalendar = Calendar.getInstance();
    private EditText editAssessmentName;
    private EditText editStartDate;
    private EditText editEndDate;
    private Spinner editAssessmentType;
    DatePickerDialog.OnDateSetListener assessmentStartDate;
    DatePickerDialog.OnDateSetListener assessmentEndDate;
    String TAG = "SchedulerDatabaseLog";
    Repository repository;
    int mCourseId;



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_add);

        mCourseId = getIntent().getIntExtra("courseId",0);

        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinner = (Spinner) findViewById(R.id.editAssessmentType);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.assessmentType, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        editStartDate = findViewById(R.id.editAssessmentStartDate);
        assessmentStartDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                assessmentStartCalendar.set(Calendar.YEAR, year);
                assessmentStartCalendar.set(Calendar.MONTH, monthOfYear);
                assessmentStartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText(editStartDate, assessmentStartCalendar);
            }
        };
        editStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AssessmentAddActivity.this, assessmentStartDate, assessmentStartCalendar
                        .get(Calendar.YEAR), assessmentStartCalendar.get(Calendar.MONTH),
                        assessmentStartCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEndDate = findViewById(R.id.editAssessmentEndDate);
        assessmentEndDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                assessmentEndCalendar.set(Calendar.YEAR, year);
                assessmentEndCalendar.set(Calendar.MONTH, monthOfYear);
                assessmentEndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText(editEndDate, assessmentEndCalendar);
            }
        };
        editEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AssessmentAddActivity.this, assessmentEndDate, assessmentEndCalendar
                        .get(Calendar.YEAR), assessmentEndCalendar.get(Calendar.MONTH),
                        assessmentEndCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }



    private void updateDateText(EditText v, Calendar calendar) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        v.setText(sdf.format(calendar.getTime()));
    }

    public void addAssessment(View view) {

        repository = new Repository(getApplication());

        editAssessmentName = findViewById(R.id.editAssessmentName);
        editStartDate = findViewById(R.id.editAssessmentStartDate);
        editEndDate = findViewById(R.id.editAssessmentEndDate);
        editAssessmentType = findViewById(R.id.editAssessmentType);

        String assessmentName = editAssessmentName.getText().toString();
        String startDate = editStartDate.getText().toString();
        String endDate = editEndDate.getText().toString();
        String assessmentType = editAssessmentType.getSelectedItem().toString();

        Assessment assessment = new Assessment(assessmentName, mCourseId, assessmentType, startDate,
                endDate);

        repository.insert(assessment);

        this.finish();

    }
    public void cancelAssessment(View view) {
        this.finish();
    }
}
