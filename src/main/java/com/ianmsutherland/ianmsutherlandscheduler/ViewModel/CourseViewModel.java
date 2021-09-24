package com.ianmsutherland.ianmsutherlandscheduler.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ianmsutherland.ianmsutherlandscheduler.Database.Repository;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Course;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Term;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    int termId;
    private Repository repository;
    private LiveData<List<Course>> mAllCourses;
    private LiveData<List<Course>> mAllCoursesForTerm;
    String TAG = "SchedulerDatabaseLog";

    public CourseViewModel(Application application, int termId) {
        super(application);
        repository = new Repository(application);
        mAllCoursesForTerm = repository.getAllCoursesForTerm(termId);
    }

    public CourseViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        mAllCourses = repository.getAllCourses();
        // mAllCoursesForTerm = repository.getAllCoursesForTerm(termId);
    }

    public LiveData<List<Course>> getAllCoursesForTerm(int termId) {
        return repository.getAllCoursesForTerm(termId);
    }

    public LiveData<List<Course>> getAllCourses() {
        return mAllCourses;
    }

    public void insert(Course course) {
        repository.insert(course);
    }

    public int lastId() {
        return mAllCourses.getValue().size();
    }

}
