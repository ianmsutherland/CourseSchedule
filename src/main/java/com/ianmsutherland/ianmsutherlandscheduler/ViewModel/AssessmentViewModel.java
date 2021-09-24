package com.ianmsutherland.ianmsutherlandscheduler.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ianmsutherland.ianmsutherlandscheduler.Database.Repository;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Assessment;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Course;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {

    int assessmentId;
    AssessmentViewModel mAssessmentViewModel;
    private Repository repository;
    private LiveData<List<Assessment>> mAllAssessments;
    private LiveData<List<Assessment>> mAllAssessmentsForCourse;
    String TAG = "SchedulerDatabaseLog";

    public AssessmentViewModel(Application application, int courseId) {
        super(application);
        repository = new Repository(application);
        mAllAssessmentsForCourse = repository.getAllAssessmentsForCourse(courseId);
    }

    public AssessmentViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        mAllAssessments = repository.getAllAssessments();
        // mAllCoursesForTerm = repository.getAllCoursesForTerm(termId);
    }

    public LiveData<List<Assessment>> getAllAssessmentsForCourse(int courseId) {
        return repository.getAllAssessmentsForCourse(courseId);
    }

    public LiveData<List<Assessment>> getAllAssessments() {
        return mAllAssessments;
    }

    public void insert(Assessment assessment) {
        repository.insert(assessment);
    }

    public int lastId() {
        return mAllAssessments.getValue().size();
    }

}
