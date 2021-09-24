package com.ianmsutherland.ianmsutherlandscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ianmsutherland.ianmsutherlandscheduler.Entities.Assessment;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Query("SELECT * FROM assessments ORDER BY id")
    LiveData<List<Assessment>> getAllAssessments();

    @Query("SELECT * FROM assessments WHERE course = :course ORDER BY startDate")
    LiveData<List<Assessment>> getAllAssessmentsForCourse(int course);

    @Query("SELECT * FROM assessments WHERE id = :id")
    public Assessment getAssessment(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insert(Assessment assessment);

    @Update
    public void update(Assessment assessment);

    @Delete
    public void delete(Assessment assessment);
}
