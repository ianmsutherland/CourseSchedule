package com.ianmsutherland.ianmsutherlandscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ianmsutherland.ianmsutherlandscheduler.Entities.Term;

import java.util.List;

@Dao
public interface TermDAO {

    @Query("SELECT * FROM terms ORDER BY startDate")
    LiveData<List<Term>> getAllTerms();

    @Query("SELECT * FROM terms WHERE id = :id")
    public Term getTerm(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insert(Term term);

    @Update
    public void update(Term term);

    @Delete
    public void delete(Term term);
}
