package com.ianmsutherland.ianmsutherlandscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ianmsutherland.ianmsutherlandscheduler.Entities.Assessment;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Course;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Term;

import java.util.List;

@Dao
public interface CourseDAO {

    @Query("SELECT * FROM courses ORDER BY startDate")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM courses WHERE term = :termId ORDER BY id")
    LiveData<List<Course>> getAllCoursesForTerm(int termId);

    @Query("SELECT * FROM courses WHERE id = :id")
    public Course getCourse(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insert(Course course);

    @Update
    public void update(Course course);

    @Delete
    public void delete(Course course);
}
