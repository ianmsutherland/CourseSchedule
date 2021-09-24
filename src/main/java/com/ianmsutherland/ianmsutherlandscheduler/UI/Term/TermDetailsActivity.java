package com.ianmsutherland.ianmsutherlandscheduler.UI.Term;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ianmsutherland.ianmsutherlandscheduler.Database.Repository;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Course;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Term;
import com.ianmsutherland.ianmsutherlandscheduler.R;
import com.ianmsutherland.ianmsutherlandscheduler.UI.Course.CourseActivity;
import com.ianmsutherland.ianmsutherlandscheduler.UI.Course.CourseAdapter;
import com.ianmsutherland.ianmsutherlandscheduler.UI.Course.CourseAddActivity;
import com.ianmsutherland.ianmsutherlandscheduler.ViewModel.CourseViewModel;
import com.ianmsutherland.ianmsutherlandscheduler.ViewModel.TermViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TermDetailsActivity extends AppCompatActivity {


    private CourseViewModel mCourseViewModel;
    private TermViewModel mTermViewModel;
    String TAG = "SchedulerDatabaseLog";

    // if the view is refreshed, set a int that will save the selected item
    private int mId = 0;
    private static String KEY_TERM_ID = "termId";
    int mTermId;
    Term mTerm;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // save the state when something is selected
        if (mId != -1) {
            savedInstanceState.putInt(KEY_TERM_ID, mId);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.delete_term:

            // first need to verify there are no courses associated with this term
            mCourseViewModel.getAllCoursesForTerm(mTermId).observe(this, new Observer<List<Course>>() {
                @Override
                public void onChanged(List<Course> courses) {
                    if (courses.size() == 0) {
                        Repository repository = new Repository(getApplication());
                        mTerm = repository.getTerm(mTermId);
                        repository.delete(mTerm);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"Unable to delete Term with courses. Please delete all courses associated with this term first.",Toast.LENGTH_LONG).show();
                    }
                }
            });
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_term_details_activity, menu);
        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        // if there was an intent sent here
        Intent intent = getIntent();
        if (intent.getIntExtra("termId", -1) != -1) {
            TextView mTermName = findViewById(R.id.termName);
            TextView mStartDate = findViewById(R.id.termStartDate);
            TextView mEndDate = findViewById(R.id.termEndDate);
            mTermId = intent.getIntExtra("termId", -1);
            mTermName.setText(getIntent().getStringExtra("termName"));
            mStartDate.setText(getIntent().getStringExtra("termStartDate"));
            mEndDate.setText(getIntent().getStringExtra("termEndDate"));
        }

        RecyclerView recyclerView = findViewById(R.id.course_recycler_view);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mCourseViewModel.getAllCoursesForTerm(mTermId).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                adapter.setCourses(courses);
            }
        });


        //set a button to create a new course
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(TermDetailsActivity.this, CourseAddActivity.class);
                intent.putExtra("termId", mTermId);
                startActivity(intent);
            }
        });






    }
}
