package com.ianmsutherland.ianmsutherlandscheduler.UI.Course;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ianmsutherland.ianmsutherlandscheduler.Database.Repository;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Course;
import com.ianmsutherland.ianmsutherlandscheduler.R;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Term;
import com.ianmsutherland.ianmsutherlandscheduler.UI.Term.TermAdapter;
import com.ianmsutherland.ianmsutherlandscheduler.ViewModel.CourseViewModel;
import com.ianmsutherland.ianmsutherlandscheduler.ViewModel.TermViewModel;

import java.util.List;

public class CourseFragment extends Fragment {
    private Term mTerm;

    private CourseViewModel mCourseViewModel;
    private Repository repository;
    private CourseAdapter adapter;
    private String TAG = "SchedulerDatabaseLog";

//    Why create a new instance to populate the fragment rather than onCreate
//        When Android re-creates a fragment, the system always remembers the fragment arguments
//        used to initially create the fragment. The newInstance() method is not called when
//        re-creating the fragment, but onCreate() is. Good practice is to write fragments that use
//        the fragment arguments in onCreate() to re-initialize the fragment instead of relying on
//        other mechanisms.
    public static CourseFragment newInstance(int termId){
        CourseFragment fragment = new CourseFragment();
        Bundle args = new Bundle();
        args.putInt("termId", termId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // in case
        int courseId = 1;

        // if there is a term id set in arguments
        if (getArguments() != null) {

            // get the term id from the arguments set in CourseFragment.newInstance
            courseId = getArguments().getInt("termId");
        }

        // populate the list
        // need to create Course DAO
        //mCourse = BandDatabase.getInstance(getContext().getTerm(bandId));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View view = inflater.inflate(R.layout.fragment_course, container, false);

         return view;

    }
}
