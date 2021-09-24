package com.ianmsutherland.ianmsutherlandscheduler.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ianmsutherland.ianmsutherlandscheduler.DAO.AssessmentDAO;
import com.ianmsutherland.ianmsutherlandscheduler.DAO.CourseDAO;
import com.ianmsutherland.ianmsutherlandscheduler.DAO.InstructorDAO;
import com.ianmsutherland.ianmsutherlandscheduler.DAO.TermDAO;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Assessment;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Course;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Instructor;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Term;

import java.util.List;

public class Repository {
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    // private InstructorDAO mInstructorDAO;

    private Term mTerm;
    private LiveData<List<Term>> mAllTerms;

    private Course mCourse;
    private LiveData<List<Course>> mAllCourses;
    private LiveData<List<Course>> mAllCoursesForTerm;

    private Assessment mAssessment;
    private LiveData<List<Assessment>> mAllAssessments;
    private LiveData<List<Assessment>> mAllAssessmentsForCourse;

    // private LiveData<List<Instructor>> mAllInstructors;
    // private int mInstructorId;
    // private String mInstructorName;
    // private String mInstructorPhone;
    // private String mInstructorEmail;

    private int termId;
    private int courseId;

    private int assessmentId;

    // private int instructorId;
    // private String instructorName;

    String TAG = "SchedulerDatabaseLog";

    public Repository(Application application) {
        SchedulerDatabaseBuilder db=SchedulerDatabaseBuilder.getDatabase(application);
        mTermDAO=db.termDAO();
        mCourseDAO=db.courseDAO();
        mAssessmentDAO=db.assessmentDAO();
        // mInstructorDAO=db.instructorDAO();

        mTerm = mTermDAO.getTerm(termId);
        mAllTerms = mTermDAO.getAllTerms();

        mCourse = mCourseDAO.getCourse(courseId);
        mAllCourses = mCourseDAO.getAllCourses();
        mAllCoursesForTerm = mCourseDAO.getAllCoursesForTerm(termId);

        mAssessment = mAssessmentDAO.getAssessment(assessmentId);
        mAllAssessments = mAssessmentDAO.getAllAssessments();
        mAllAssessmentsForCourse = mAssessmentDAO.getAllAssessmentsForCourse(courseId);

        // mAllInstructors = mInstructorDAO.getAllInstructors();
        // mInstructorId = mInstructorDAO.getInstructorId(instructorName);
        // mInstructorPhone = mInstructorDAO.getInstructorPhone(instructorId);
        // mInstructorEmail = mInstructorDAO.getInstructorEmail(instructorId);
    }

    public LiveData<List<Term>> getAllTerms() {
        return mAllTerms;
    }
    public Term getTerm(int termId) {
        return mTermDAO.getTerm(termId);
    }

    public LiveData<List<Course>> getAllCourses() {
        return mAllCourses;
    }
    public Course getCourse(int courseId) {
        return mCourseDAO.getCourse(courseId);
    }
    public LiveData<List<Course>> getAllCoursesForTerm(int termId) {
        return mCourseDAO.getAllCoursesForTerm(termId);
    }


    // public LiveData<List<Instructor>> getAllInstructors() {
    //     return mAllInstructors;
    // }
    // public String getInstructor(int instructorId) {
    //     return mInstructorDAO.getInstructor(instructorId);
    // }
    // public int getInstructorId(String instructorName) {
    //     return mInstructorDAO.getInstructorId(instructorName);
    // }
    // public String getInstructorPhone(int instructorId) {
    //     return mInstructorDAO.getInstructorPhone(instructorId);
    // }
    // public String getInstructorEmail(int instructorId) {
    //     return mInstructorDAO.getInstructorEmail(instructorId);
    // }

    public LiveData<List<Assessment>> getAllAssessments() {
        return mAllAssessments;
    }
    public Assessment getAssessment(int assessmentId) {
        return mAssessmentDAO.getAssessment(assessmentId);
    }
    public LiveData<List<Assessment>> getAllAssessmentsForCourse(int courseId) {
        return mAllAssessmentsForCourse;
    }














    public void insert(Term term) {
        new insertAsyncTaskTerm(mTermDAO).execute(term);
    }
    private static class insertAsyncTaskTerm extends AsyncTask<Term, Void, Void> {

        private TermDAO mAsyncTaskDAO;

        insertAsyncTaskTerm(TermDAO dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Term... params) {
            mAsyncTaskDAO.insert(params[0]);
            return null;
        }
    }

    public void insert(Course course) {
        new insertAsyncTaskCourse(mCourseDAO).execute(course);
    }
    private static class insertAsyncTaskCourse extends AsyncTask<Course, Void, Void> {

        private CourseDAO mAsyncTaskDAO;

        insertAsyncTaskCourse(CourseDAO dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Course... params) {
            mAsyncTaskDAO.insert(params[0]);
            return null;
        }
    }

    public void insert(Assessment assessment) {
        new insertAsyncTaskAssessment(mAssessmentDAO).execute(assessment);
    }
    private static class insertAsyncTaskAssessment extends AsyncTask<Assessment, Void, Void> {

        private AssessmentDAO mAsyncTaskDAO;

        insertAsyncTaskAssessment(AssessmentDAO dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Assessment... params) {
            mAsyncTaskDAO.insert(params[0]);
            return null;
        }
    }

    // public void insert(Instructor instructor) {
    //     new insertAsyncTaskInstructor(mInstructorDAO).execute(instructor);
    // }
    // private static class insertAsyncTaskInstructor extends AsyncTask<Instructor, Void, Void> {
    //
    //     private InstructorDAO mAsyncTaskDAO;
    //
    //     insertAsyncTaskInstructor(InstructorDAO dao) {
    //         mAsyncTaskDAO = dao;
    //     }
    //
    //     @Override
    //     protected Void doInBackground(final Instructor... params) {
    //         mAsyncTaskDAO.insert(params[0]);
    //         return null;
    //     }
    // }

    public void delete(Term term) {
        new deleteAsyncTaskTerm(mTermDAO).execute(term);
    }
    private static class deleteAsyncTaskTerm extends AsyncTask<Term, Void, Void> {

        private TermDAO mAsyncTaskDAO;

        deleteAsyncTaskTerm(TermDAO dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Term... params) {
            mAsyncTaskDAO.delete(params[0]);
            return null;
        }
    }

    public void delete(Course course) {
        new deleteAsyncTaskCourse(mCourseDAO).execute(course);
    }
    private static class deleteAsyncTaskCourse extends AsyncTask<Course, Void, Void> {

        private CourseDAO mAsyncTaskDAO;

        deleteAsyncTaskCourse(CourseDAO dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Course... params) {
            mAsyncTaskDAO.delete(params[0]);
            return null;
        }
    }

    public void delete(Assessment assessment) {
        new deleteAsyncTaskAssessment(mAssessmentDAO).execute(assessment);
    }
    private static class deleteAsyncTaskAssessment extends AsyncTask<Assessment, Void, Void> {

        private AssessmentDAO mAsyncTaskDAO;

        deleteAsyncTaskAssessment(AssessmentDAO dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Assessment... params) {
            mAsyncTaskDAO.delete(params[0]);
            return null;
        }
    }

    public void update(Course course) {
        new updateAsyncTaskCourse(mCourseDAO).execute(course);
    }
    private static class updateAsyncTaskCourse extends AsyncTask<Course, Void, Void> {

        private CourseDAO mAsyncTaskDAO;

        updateAsyncTaskCourse(CourseDAO dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Course... params) {
            mAsyncTaskDAO.update(params[0]);
            return null;
        }
    }

    public void update(Assessment assessment) {
        new updateAsyncTaskAssessment(mAssessmentDAO).execute(assessment);
    }
    private static class updateAsyncTaskAssessment extends AsyncTask<Assessment, Void, Void> {

        private AssessmentDAO mAsyncTaskDAO;

        updateAsyncTaskAssessment(AssessmentDAO dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Assessment... params) {
            mAsyncTaskDAO.update(params[0]);
            return null;
        }
    }

    // public void update(Instructor instructor) {
    //     new updateAsyncTaskInstructor(mInstructorDAO).execute(instructor);
    // }
    // private static class updateAsyncTaskInstructor extends AsyncTask<Instructor, Void, Void> {
    //
    //     private InstructorDAO mAsyncTaskDAO;
    //
    //     updateAsyncTaskInstructor(InstructorDAO dao) {
    //         mAsyncTaskDAO = dao;
    //     }
    //
    //     @Override
    //     protected Void doInBackground(final Instructor... params) {
    //         mAsyncTaskDAO.update(params[0]);
    //         return null;
    //     }
    // }
}
