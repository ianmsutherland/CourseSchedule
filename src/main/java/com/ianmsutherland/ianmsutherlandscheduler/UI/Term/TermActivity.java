package com.ianmsutherland.ianmsutherlandscheduler.UI.Term;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ianmsutherland.ianmsutherlandscheduler.Database.Repository;
import com.ianmsutherland.ianmsutherlandscheduler.R;
import com.ianmsutherland.ianmsutherlandscheduler.UI.Course.CourseActivity;

import java.util.Objects;

public class TermActivity extends AppCompatActivity implements TermFragment.OnTermSelectedListener {

    private Repository repository;
    public FragmentManager fragmentManager;
    String TAG = "SchedulerDatabaseLog";

    // if the view is refreshed, set a int that will save the selected item
    private int mId = 0;
    private static String KEY_TERM_ID = "termId";

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // save the state when something is selected
        if (mId != -1 ) {
            savedInstanceState.putInt(KEY_TERM_ID, mId);
        }
    }

    @Override
    public void onTermSelected(int termId) {

        // if the view allow for the details course fragment to exist, send an intent to populate it
        //  with the clicked term
        if (findViewById(R.id.course_fragment_container) == null) {

            // no course fragment exists, so likely running on phone
            // THIS CODE WILL CHANGE WHEN TABLET LAYOUT IS IMPLEMENTED
            // Send the term of the clicked button to DetailsActivity
            Intent intent = new Intent(this, CourseActivity.class);
            intent.putExtra(CourseActivity.EXTRA_TERM_ID, String.valueOf(termId));
            startActivity(intent);
        }
    }

    // When one of the menu options are selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);



        // create a new FragmentManager (while will manager all the fragments)
        fragmentManager = getSupportFragmentManager();

        // use the fragmentManager to search for a fragment by the name list_fragment_container
        // this is defined in activity_list.xml
        Fragment fragment = fragmentManager.findFragmentById(R.id.term_fragment_container);

        // if the fragment is not found
        if (fragment == null) {

            // create a new fragment
            fragment = new TermFragment();

            // begin the setup of fragment
            fragmentManager.beginTransaction()
                    // add list_fragment_container.xml to the fragmentManager
                    .replace(R.id.term_fragment_container, fragment)
                    .commit();
        }
    }

    public void addTerm(View view) {
        Intent intent=new Intent(TermActivity.this, TermAddActivity.class);
        startActivity(intent);
    }
}