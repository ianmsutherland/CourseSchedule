package com.ianmsutherland.ianmsutherlandscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ianmsutherland.ianmsutherlandscheduler.Entities.Instructor;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Term;

import java.util.List;

@Dao
public interface InstructorDAO {

    @Query("SELECT * FROM instructors ORDER BY id")
    LiveData<List<Instructor>> getAllInstructors();

    @Query("SELECT name FROM instructors WHERE id = :id")
    public String getInstructor(int id);

    @Query("SELECT phoneNumber FROM instructors WHERE id = :id")
    public String getInstructorPhone(int id);

    @Query("SELECT email FROM instructors WHERE id = :id")
    public String getInstructorEmail(int id);

    @Query("SELECT id FROM instructors WHERE name = :name")
    public int getInstructorId(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insert(Instructor instructor);

    @Update
    public void update(Instructor instructor);

    @Delete
    public void delete(Instructor instructor);
}
