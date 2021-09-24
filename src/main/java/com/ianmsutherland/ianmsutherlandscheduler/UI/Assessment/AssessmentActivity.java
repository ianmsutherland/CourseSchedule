package com.ianmsutherland.ianmsutherlandscheduler.UI.Assessment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ianmsutherland.ianmsutherlandscheduler.R;
import com.ianmsutherland.ianmsutherlandscheduler.UI.Course.CourseFragment;

import java.util.Objects;

public class AssessmentActivity extends AppCompatActivity {

    // set the int that will grab the term id from intent
    // it's final because if another term is selected, the activity refreshes
    public static final String EXTRA_TERM_ID = "term";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_assessment);

        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // set a floating action button to create a new course
        // FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        // fab.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View view) {
        //         Intent intent = new Intent(AssessmentActivity.this, AssessmentAddActivity.class);
        //         intent.putExtra("termId",EXTRA_TERM_ID);
        //         startActivity(intent);
        //     }
        // });

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.course_fragment_container);

        // if the fragment is not set
        if (fragment == null) {
            // set NO TERM SELECTED TEXT

            int termId = getIntent().getIntExtra(EXTRA_TERM_ID,1);

            fragment = CourseFragment.newInstance(termId);
            fragmentManager.beginTransaction()
                    .add(R.id.course_fragment_container, fragment)
                    .commit();
        }
    }
}
