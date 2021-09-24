package com.ianmsutherland.ianmsutherlandscheduler.UI.Course;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ianmsutherland.ianmsutherlandscheduler.Database.Repository;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Assessment;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Course;
import com.ianmsutherland.ianmsutherlandscheduler.R;
import com.ianmsutherland.ianmsutherlandscheduler.UI.Assessment.AssessmentAdapter;
import com.ianmsutherland.ianmsutherlandscheduler.UI.Assessment.AssessmentAddActivity;
import com.ianmsutherland.ianmsutherlandscheduler.ViewModel.AssessmentViewModel;
import com.ianmsutherland.ianmsutherlandscheduler.ViewModel.CourseViewModel;
import com.ianmsutherland.ianmsutherlandscheduler.ViewModel.Receiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CourseDetailsActivity extends AppCompatActivity {


    private CourseViewModel mCourseViewModel;
    private AssessmentViewModel mAssessmentViewModel;
    String TAG = "SchedulerDatabaseLog";

    // if the view is refreshed, set a int that will save the selected item
    private int mId = 0;
    private static String KEY_COURSE_ID = "termId";
    int mCourseId;
    private int mTermId;

    final Calendar courseStartCalendar = Calendar.getInstance();
    final Calendar courseEndCalendar = Calendar.getInstance();
    private EditText editCourseName;
    private EditText editCourseStartDate;
    private EditText editCourseEndDate;
    private Spinner editCourseStatus;
    private EditText editCourseInstructorName;
    private EditText editCourseInstructorEmail;
    private EditText editCourseInstructorPhone;
    private EditText editCourseNote;
    private EditText instructorEmailText;
    private EditText instructorPhoneText;

    DatePickerDialog.OnDateSetListener courseStartDateListener;
    DatePickerDialog.OnDateSetListener courseEndDateListener;
    private int mInstructorId;
    private String mInstructorPhone;
    private String mInstructorEmail;
    private String mInstructorName;
    private String mName;
    private String mStartDate;
    private String mEndDate;
    private String mStatusName;
    private String mNote;
    Repository repository;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // save the state when something is selected
        if (mId != -1 ) {
            savedInstanceState.putInt(KEY_COURSE_ID, mId);
        }
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
        String title;
        AlarmManager alarmManager;
        PendingIntent sender;

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.delete_course:
                // get the course you need to delete via it's ID
                Repository repository = new Repository(getApplication());
                Course mCourse = repository.getCourse(mCourseId);
                repository.delete(mCourse);
                finish();
                break;
            case R.id.share_notes:
                intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, mNote);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Note for course " + mName);
                intent.setType("text/plain");
                startActivity(intent);
                break;
            case R.id.notify_course_start:
                Toast.makeText(getApplicationContext(),"Creating notification...",Toast.LENGTH_LONG).show();

                String startText = "Reminder: Your course " + mName + " starts today.";
                title = mName;
                long startDate = convertDateToMilli(mStartDate);

                alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                intent = new Intent(CourseDetailsActivity.this, Receiver.class);

                intent.putExtra("text",startText);
                intent.putExtra("title", title);
                intent.putExtra("courseId", mCourseId);

                sender = PendingIntent.getBroadcast(CourseDetailsActivity.this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, startDate, sender);
                break;
            case R.id.notify_course_end:
                Toast.makeText(getApplicationContext(),"Creating notification...",Toast.LENGTH_LONG).show();

                String endText = "Reminder: Your course " + mName + " ends today.";
                long endDate = convertDateToMilli(mEndDate);
                title = mName;

                alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                intent = new Intent(CourseDetailsActivity.this, Receiver.class);

                intent.putExtra("text",endText);
                intent.putExtra("title", title);
                intent.putExtra("courseId", mCourseId);

                sender = PendingIntent.getBroadcast(CourseDetailsActivity.this,0,intent,  PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, endDate, sender);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_details_activity, menu);
        return true;
    }
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);



        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // if there was an intent sent here
        if (getIntent().getIntExtra("courseId",-1) != -1) {

            Repository repository = new Repository(getApplication());

            editCourseName = findViewById(R.id.editCourseName);
            editCourseStartDate = findViewById(R.id.editCourseStartDate);
            editCourseEndDate = findViewById(R.id.editCourseEndDate);
            editCourseStatus = (Spinner) findViewById(R.id.editCourseStatus);
            editCourseInstructorName = findViewById(R.id.editCourseInstructorName);
            editCourseInstructorPhone = findViewById(R.id.editCourseInstructorPhone);
            editCourseInstructorEmail = findViewById(R.id.editCourseInstructorEmail);
            editCourseNote = findViewById(R.id.editCourseNote);

            mCourseId = getIntent().getIntExtra("courseId",-1);
            mName = getIntent().getStringExtra("courseName");
            mStartDate = getIntent().getStringExtra("courseStartDate");
            mEndDate = getIntent().getStringExtra("courseEndDate");
            mStatusName = getIntent().getStringExtra("courseStatus");
            mNote = getIntent().getStringExtra("courseNote");
            mInstructorName = getIntent().getStringExtra("courseInstructorName");
            mInstructorPhone = getIntent().getStringExtra("courseInstructorPhone");
            mInstructorEmail = getIntent().getStringExtra("courseInstructorEmail");
            mTermId = getIntent().getIntExtra("courseTerm",0);

            editCourseName.setText(mName);
            editCourseStartDate.setText(mStartDate);
            editCourseEndDate.setText(mEndDate);
            editCourseInstructorName.setText(mInstructorName);
            editCourseInstructorPhone.setText(mInstructorPhone);
            editCourseInstructorEmail.setText(mInstructorEmail);
            editCourseNote.setText(mNote);


            // get the instructor name using the instructor Id sent from the intent
            // mInstructorName = repository.getInstructor(mInstructorId);

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> courseStatusAdapter = ArrayAdapter.createFromResource(this,
                    R.array.courseStatus, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            courseStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            editCourseStatus.setAdapter(courseStatusAdapter);

            // loop through the spinner items to get the position of the one that matches the intent
            for (int i = 0; i < editCourseStatus.getCount(); i++) {
                if (editCourseStatus.getItemAtPosition(i).toString().equals(mStatusName)) {
                    editCourseStatus.setSelection(i);
                }
            }

            // // Create an ArrayAdapter using the string array and a default spinner layout
            // ArrayAdapter<CharSequence> courseInstructorAdapter = ArrayAdapter.createFromResource(this,
            //         R.array.instructors, android.R.layout.simple_spinner_item);
            // // Specify the layout to use when the list of choices appears
            // courseInstructorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // // Apply the adapter to the spinner
            // editCourseInstructorName.setAdapter(courseInstructorAdapter);
            // for(int i = 0; i < editCourseInstructorName.getCount(); i++) {
            //     if (editCourseInstructorName.getItemAtPosition(i).toString().equals(mInstructorName)) {
            //         editCourseInstructorName.setSelection(i);
            //     }
            //
            //
            //     editCourseInstructorName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //         @Override
            //         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //             Repository repository = new Repository(getApplication());
            //             mInstructorId = repository.getInstructorId(editCourseInstructorName.getSelectedItem().toString());
            //             mInstructorPhone = repository.getInstructorPhone(mInstructorId);
            //             mInstructorEmail = repository.getInstructorEmail(mInstructorId);
            //             instructorPhoneText.setText(mInstructorPhone);
            //             instructorEmailText.setText(mInstructorEmail);
            //         }
            //
            //         @Override
            //         public void onNothingSelected(AdapterView<?> parent) {
            //         }
            //     });            }

        }
        else {
            Log.d(TAG, "No course Id");
        }

        RecyclerView recyclerView = findViewById(R.id.assessment_recycler_view);
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(@Nullable final List<Assessment> assessment) {
                // Update the cached copy of the words in the adapter.
                List<Assessment> filteredWords = new ArrayList<>();
                for (Assessment a : assessment) {
                    if (a.getCourse() == mCourseId) {
                        filteredWords.add(a);
                    }
                }
                adapter.setAssessments(filteredWords);
            }
        });



        //set a button to create a new course
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetailsActivity.this, AssessmentAddActivity.class);
                intent.putExtra("courseId",mCourseId);
                startActivity(intent);
            }
        });


        editCourseStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseDetailsActivity.this, courseStartDateListener, courseStartCalendar
                        .get(Calendar.YEAR), courseStartCalendar.get(Calendar.MONTH),
                        courseStartCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        courseStartDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                courseStartCalendar.set(Calendar.YEAR, year);
                courseStartCalendar.set(Calendar.MONTH, monthOfYear);
                courseStartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText(editCourseStartDate, courseStartCalendar);
            }
        };



        editCourseEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseDetailsActivity.this, courseEndDateListener, courseEndCalendar
                        .get(Calendar.YEAR), courseEndCalendar.get(Calendar.MONTH),
                        courseEndCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        courseEndDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                courseEndCalendar.set(Calendar.YEAR, year);
                courseEndCalendar.set(Calendar.MONTH, monthOfYear);
                courseEndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText(editCourseEndDate, courseEndCalendar);
            }
        };
    }

    // called when a date is selected, update the corresponding textView with the new date
    private void updateDateText(EditText v, Calendar calendar) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        v.setText(sdf.format(calendar.getTime()));
    }


    public void updateCourseButton(View view) {

        repository = new Repository(getApplication());

        // editCourseName = findViewById(R.id.editCourseName);
        // editCourseStartDate = findViewById(R.id.editAssessmentStartDate);
        // editCourseEndDate = findViewById(R.id.editAssessmentEndDate);
        // editCourseStatus = findViewById(R.id.editCourseStatus);
        // editCourseInstructor = findViewById(R.id.editCourseInstructor);
        // editCourseNote = findViewById(R.id.editCourseNote);
        // mTermId = getIntent().getIntExtra("courseTerm",0);

        String courseName = editCourseName.getText().toString();
        String startDate = editCourseStartDate.getText().toString();
        String endDate = editCourseEndDate.getText().toString();
        String status = editCourseStatus.getSelectedItem().toString();
        String instructorName = editCourseInstructorName.getText().toString();
        String instructorEmail = editCourseInstructorEmail.getText().toString();
        String instructorPhone = editCourseInstructorPhone.getText().toString();
        String note = editCourseNote.getText().toString();

        // get instructor Id from name
        // int instructor = repository.getInstructorId(instructorName);

        Course mCourse = repository.getCourse(mCourseId);
        mCourse.setName(courseName);
        mCourse.setStartDate(startDate);
        mCourse.setEndDate(endDate);
        mCourse.setStatus(status);
        mCourse.setInstructorName(instructorName);
        mCourse.setInstructorPhone(instructorPhone);
        mCourse.setInstructorEmail(instructorEmail);
        mCourse.setNote(note);

        repository.update(mCourse);;

        finish();

    }


    public void cancelCourse(View view) {
        this.finish();
    }
}
