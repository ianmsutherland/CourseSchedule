package com.ianmsutherland.ianmsutherlandscheduler.UI.Assessment;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ianmsutherland.ianmsutherlandscheduler.Database.Repository;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Assessment;
import com.ianmsutherland.ianmsutherlandscheduler.R;
import com.ianmsutherland.ianmsutherlandscheduler.ViewModel.AssessmentViewModel;
import com.ianmsutherland.ianmsutherlandscheduler.ViewModel.CourseViewModel;
import com.ianmsutherland.ianmsutherlandscheduler.ViewModel.Receiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AssessmentDetailsActivity extends AppCompatActivity {



    final Calendar assessmentStartCalendar = Calendar.getInstance();
    final Calendar assessmentEndCalendar = Calendar.getInstance();
    private CourseViewModel mCourseViewModel;
    private AssessmentViewModel mAssessmentViewModel;
    String TAG = "SchedulerDatabaseLog";

    DatePickerDialog.OnDateSetListener assessmentStartDate;
    DatePickerDialog.OnDateSetListener assessmentEndDate;
    private EditText editAssessmentNameView;
    private EditText editStartDateView;
    private EditText editEndDateView;
    private Spinner editType;
    private String mAssessmentName;
    private String mStartDate;
    private String mEndDate;

    private int instructorId;
    Repository repository;

    // if the view is refreshed, set a int that will save the selected item
    private int mId = 0;
    private static String KEY_ASSESSMENT_ID = "assessmentId";
    int mAssessmentId;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // save the state when something is selected
        if (mId != -1 ) {
            savedInstanceState.putInt(KEY_ASSESSMENT_ID, mId);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assessment_details_activity, menu);
        return true;
    }

    public long convertDateToMilli(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            //Date startDate =
            Date mDate = sdf.parse(date);
            long milliseconds = mDate.getTime();
            return milliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        PendingIntent sender;
        String title;
        Repository repository = new Repository(getApplication());
        AlarmManager alarmManager;

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.delete_assessment:
                // get the course you need to delete via it's ID
                Assessment mAssessment = repository.getAssessment(mAssessmentId);
                repository.delete(mAssessment);
                finish();
                break;
            case R.id.notify_assessment_start:
                Toast.makeText(getApplicationContext(),"Creating notification...",Toast.LENGTH_LONG).show();

                String startText = "Reminder: Your assessment " + mAssessmentName + " starts today.";
                title = mAssessmentName;
                long startDate = convertDateToMilli(mStartDate);

                alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                intent = new Intent(AssessmentDetailsActivity.this, Receiver.class);

                intent.putExtra("text",startText);
                intent.putExtra("title", title);
                intent.putExtra("courseId", mAssessmentName);
                sender = PendingIntent.getBroadcast(AssessmentDetailsActivity.this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, startDate, sender);

                break;
            case R.id.notify_assessment_end:
                Toast.makeText(getApplicationContext(),"Creating notification...",Toast.LENGTH_LONG).show();

                String endText = "Reminder: Your assessment " + mAssessmentName + " ends today.";
                title = mAssessmentName;
                long endDate = convertDateToMilli(mEndDate);

                alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                intent = new Intent(AssessmentDetailsActivity.this, Receiver.class);

                intent.putExtra("text",endText);
                intent.putExtra("title", title);
                intent.putExtra("courseId", mAssessmentName);

                sender = PendingIntent.getBroadcast(AssessmentDetailsActivity.this,0,intent,  PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, endDate, sender);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // if there was an intent sent here
        if (getIntent().getIntExtra("courseId",-1) != -1) {
            EditText mAssessmentNameView = findViewById(R.id.editAssessmentName);
            editStartDateView = findViewById(R.id.editAssessmentStartDate);
            editEndDateView = findViewById(R.id.editAssessmentEndDate);
            Spinner mType = findViewById(R.id.editAssessmentType);

            mAssessmentName = getIntent().getStringExtra("assessmentName");
            mAssessmentId = getIntent().getIntExtra("assessmentId",-1);
            mStartDate = getIntent().getStringExtra("assessmentStartDate");
            mEndDate = getIntent().getStringExtra("assessmentEndDate");

            mAssessmentNameView.setText(mAssessmentName);
            editStartDateView.setText(mStartDate);
            editEndDateView.setText(mEndDate);
            String mTypeName = getIntent().getStringExtra("assessmentType");

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.assessmentType, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            mType.setAdapter(adapter);

            // loop through the spinner items to get the position of the one that matches the intent
            for (int i = 0; i < mType.getCount(); i++) {
                if (mType.getItemAtPosition(i).equals(mTypeName))
                    mType.setSelection(i);
            }
        }

        assessmentStartDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                assessmentStartCalendar.set(Calendar.YEAR, year);
                assessmentStartCalendar.set(Calendar.MONTH, monthOfYear);
                assessmentStartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText(editStartDateView, assessmentStartCalendar);
            }
        };
        editStartDateView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AssessmentDetailsActivity.this, assessmentStartDate, assessmentStartCalendar
                        .get(Calendar.YEAR), assessmentStartCalendar.get(Calendar.MONTH),
                        assessmentStartCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEndDateView = findViewById(R.id.editAssessmentEndDate);
        assessmentEndDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                assessmentEndCalendar.set(Calendar.YEAR, year);
                assessmentEndCalendar.set(Calendar.MONTH, monthOfYear);
                assessmentEndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText(editEndDateView, assessmentEndCalendar);
            }
        };
        editEndDateView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AssessmentDetailsActivity.this, assessmentEndDate, assessmentEndCalendar
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

    public void updateAssessmentButton(View view) {

        repository = new Repository(getApplication());

        editAssessmentNameView = findViewById(R.id.editAssessmentName);
        editStartDateView = findViewById(R.id.editAssessmentStartDate);
        editEndDateView = findViewById(R.id.editAssessmentEndDate);
        editType = findViewById(R.id.editAssessmentType);
        mAssessmentId = getIntent().getIntExtra("assessmentId",0);

        String assessmentName = editAssessmentNameView.getText().toString();
        String startDate = editStartDateView.getText().toString();
        String endDate = editEndDateView.getText().toString();
        String type = editType.getSelectedItem().toString();

        Assessment mAssessment = repository.getAssessment(mAssessmentId);
        mAssessment.setName(assessmentName);
        mAssessment.setStartDate(startDate);
        mAssessment.setEndDate(endDate);
        mAssessment.setType(type);
        repository.update(mAssessment);

        finish();

    }




    public void cancelAssessment(View view) {
        this.finish();
    }
}
