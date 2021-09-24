package com.ianmsutherland.ianmsutherlandscheduler.Entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms"
        ,indices = {@Index(value={"id"},unique = true)}
        )
public class Term {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "startDate")
    private String mStartDate;

    @ColumnInfo(name = "endDate")
    private String mEndDate;

    public Term(String name, String startDate, String endDate) {
        this.mName = name;
        this.mStartDate = startDate;
        this.mEndDate = endDate;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String startDate) {
        this.mStartDate = startDate;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String endDate) {
        this.mEndDate = endDate;
    }

}
