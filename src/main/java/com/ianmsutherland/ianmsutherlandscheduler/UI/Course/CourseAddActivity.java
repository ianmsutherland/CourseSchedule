package com.ianmsutherland.ianmsutherlandscheduler.UI.Course;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ianmsutherland.ianmsutherlandscheduler.Database.Repository;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Course;
import com.ianmsutherland.ianmsutherlandscheduler.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class CourseAddActivity extends AppCompatActivity {

    final Calendar courseStartCalendar = Calendar.getInstance();
    final Calendar courseEndCalendar = Calendar.getInstance();
    private EditText editCourseName;
    private EditText editStartDate;
    private EditText editEndDate;
    private EditText editInstructorName;
    private EditText editInstructorEmail;
    private EditText editInstructorPhone;
    private Spinner editCourseStatus;
    private EditText editCourseNote;

    DatePickerDialog.OnDateSetListener courseStartDate;
    DatePickerDialog.OnDateSetListener courseEndDate;
    String TAG = "SchedulerDatabaseLog";
    Repository repository;

    private String mCourseName;
    private String mStartDate;
    private String mEndDate;
    private String mInstructorName;
    private String mInstructorPhone;
    private String mInstructorEmail;
    private String mStatus;
    private String mNote;
    private int mTermId;
    //private int mInstructorId;

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
        setContentView(R.layout.activity_course_add);

        // get the Term Id from TermDetailsActivity
        //  will be used when adding a course to associate with this term
        mTermId = getIntent().getIntExtra("termId",0);

        // add a appbar
        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editCourseName = findViewById(R.id.editCourseName);
        editStartDate = findViewById(R.id.editCourseStartDate);
        editEndDate = findViewById(R.id.editCourseEndDate);
        editInstructorName = findViewById(R.id.editCourseInstructorName);
        editInstructorEmail = findViewById(R.id.editCourseInstructorEmail);
        editInstructorPhone = findViewById(R.id.editCourseInstructorPhone);
        editCourseStatus = (Spinner) findViewById(R.id.editCourseStatus);
        editCourseNote = findViewById(R.id.editCourseNote);

        // create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> courseStatusAdapter = ArrayAdapter.createFromResource(this,
                R.array.courseStatus, android.R.layout.simple_spinner_item);

        // specify the layout to use when the list of choices appears
        courseStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // apply the adapter to the spinner
        //  the adapter is used to update the spinner's text
        editCourseStatus.setAdapter(courseStatusAdapter);


        // // create an ArrayAdapter using the string array and a default spinner layout
        // ArrayAdapter<CharSequence> courseInstructorAdapter = ArrayAdapter.createFromResource(this,
        //         R.array.instructors, android.R.layout.simple_spinner_item);
        //
        // // specify the layout to use when the list of choices appears
        // courseInstructorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //
        // // apply the adapter to the spinner
        // //  the adapter is used to update the spinner's text
        // editInstructor.setAdapter(courseInstructorAdapter);
        //
        //
        // editInstructor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        //     @Override
        //     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //         Repository repository = new Repository(getApplication());
        //         mInstructorId = repository.getInstructorId(editInstructor.getSelectedItem().toString());
        //         mInstructorPhone = repository.getInstructorPhone(mInstructorId);
        //         mInstructorEmail = repository.getInstructorEmail(mInstructorId);
        //         instructorPhoneView.setText(mInstructorPhone);
        //         instructorEmailView.setText(mInstructorEmail);
        //     }
        //
        //     @Override
        //     public void onNothingSelected(AdapterView<?> parent) {
        //     }
        // });




        courseStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                courseStartCalendar.set(Calendar.YEAR, year);
                courseStartCalendar.set(Calendar.MONTH, monthOfYear);
                courseStartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText(editStartDate, courseStartCalendar);
            }
        };

        editStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseAddActivity.this, courseStartDate, courseStartCalendar
                        .get(Calendar.YEAR), courseStartCalendar.get(Calendar.MONTH),
                        courseStartCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        courseEndDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                courseEndCalendar.set(Calendar.YEAR, year);
                courseEndCalendar.set(Calendar.MONTH, monthOfYear);
                courseEndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText(editEndDate, courseEndCalendar);
            }
        };
        editEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseAddActivity.this, courseEndDate, courseEndCalendar
                        .get(Calendar.YEAR), courseEndCalendar.get(Calendar.MONTH),
                        courseEndCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    // called when a date is selected, update the corresponding textView with the new date
    private void updateDateText(EditText v, Calendar calendar) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        v.setText(sdf.format(calendar.getTime()));
    }



    public void addCourse(View view) {

        repository = new Repository(getApplication());

        // editCourseName = findViewById(R.id.editCourseName);
        // editStartDate = findViewById(R.id.editAssessmentStartDate);
        // editEndDate = findViewById(R.id.editAssessmentEndDate);
        // editCourseStatus = findViewById(R.id.editCourseStatus);
        // editInstructor = findViewById(R.id.editCourseInstructor);
        // editCourseNote = findViewById(R.id.editCourseNote);

        mCourseName = editCourseName.getText().toString();
        mStartDate = editStartDate.getText().toString();
        mEndDate = editEndDate.getText().toString();
        mInstructorName = editInstructorName.getText().toString();
        mInstructorPhone = editInstructorPhone.getText().toString();
        mInstructorEmail = editInstructorEmail.getText().toString();
        mStatus = editCourseStatus.getSelectedItem().toString();
        mNote = editCourseNote.getText().toString();

        // get instructor Id from name
        // mInstructorId = repository.getInstructorId(mInstructorName);

        Course course = new Course(mCourseName, mTermId, mStartDate, mEndDate, mStatus, mInstructorName, mInstructorPhone, mInstructorEmail, mNote);

        repository.insert(course);

        this.finish();


    }

    public void cancelCourse(View view) {
        this.finish();
    }
}
