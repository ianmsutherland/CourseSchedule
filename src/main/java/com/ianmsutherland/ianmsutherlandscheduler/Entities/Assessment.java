package com.ianmsutherland.ianmsutherlandscheduler.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments",
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "id",
                childColumns = "course")
        ,indices = {
        @Index(value = "id", unique = true),
        @Index(value = "course")
    }
)
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "course")
    private int mCourse;

    @ColumnInfo(name = "type")
    private String mType;

    @ColumnInfo(name = "startDate")
    private String mStartDate;

    @ColumnInfo(name = "endDate")
    private String mEndDate;

    public Assessment(String name, int course, String type, String startDate, String endDate) {
        this.mName = name;
        this.mCourse = course;
        this.mType = type;
        this.mStartDate = startDate;
        this.mEndDate = endDate;
    }


    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getCourse() {
        return mCourse;
    }

    public void setCourse(int course) {
        this.mCourse = course;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
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
