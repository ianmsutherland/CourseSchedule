package com.ianmsutherland.ianmsutherlandscheduler.UI.Course;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ianmsutherland.ianmsutherlandscheduler.R;

import java.util.Objects;

public class CourseActivity extends AppCompatActivity {

    // set the int that will grab the term id from intent
    // it's final because if another term is selected, the activity refreshes
    public static final String EXTRA_TERM_ID = "term";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_course);

        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


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
