package com.ianmsutherland.ianmsutherlandscheduler.Database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ianmsutherland.ianmsutherlandscheduler.DAO.AssessmentDAO;
import com.ianmsutherland.ianmsutherlandscheduler.DAO.CourseDAO;
import com.ianmsutherland.ianmsutherlandscheduler.DAO.InstructorDAO;
import com.ianmsutherland.ianmsutherlandscheduler.DAO.TermDAO;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Assessment;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Course;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Instructor;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Term;

import java.util.List;

@Database(entities = {Term.class, Course.class, Assessment.class, Instructor.class}, version = 10, exportSchema = false)
public abstract class SchedulerDatabaseBuilder extends RoomDatabase {

    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract InstructorDAO instructorDAO();
    static String TAG = "SchedulerDatabaseLog";

    // database name
    private static final String DATABASE_NAME = "scheduler.db";

    // database instance
    private static SchedulerDatabaseBuilder mSchedulerDatabaseBuilder;

    //or use the way taught int the webinar
    private static volatile SchedulerDatabaseBuilder INSTANCE;
    static SchedulerDatabaseBuilder getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (SchedulerDatabaseBuilder.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, SchedulerDatabaseBuilder.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
    return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // new PopulateDbAsync(INSTANCE).execute();


        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final InstructorDAO mInstructorDao;

        PopulateDbAsync(SchedulerDatabaseBuilder db) {
            mInstructorDao = db.instructorDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            LiveData<List<Instructor>> mAllInstructors = mInstructorDao.getAllInstructors();

            if (mAllInstructors.getValue() == null) {
                Instructor instructor = new Instructor(1, "Albert Einstein", "187-919-5576", "albert.einstein@zurich.edu");
                mInstructorDao.insert(instructor);

                instructor = new Instructor(2, "Aristotle", "038–403–2262", "aristotle@stagira.gr");
                mInstructorDao.insert(instructor);

                instructor = new Instructor(3, "Maria Montessori", "187–019–5281", "mmontessori@rome.it");
                mInstructorDao.insert(instructor);

                instructor = new Instructor(4, "Plato", "042–804–2780", "aristocles@athens.gr");
                mInstructorDao.insert(instructor);

                instructor = new Instructor(5, "Confucius", "055–104–7972", "master_kong@qui.cn");
                mInstructorDao.insert(instructor);
            }
            return null;
        }
    }
}
