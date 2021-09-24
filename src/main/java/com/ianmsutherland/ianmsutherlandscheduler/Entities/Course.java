package com.ianmsutherland.ianmsutherlandscheduler.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses",
        foreignKeys = {
                @ForeignKey(
                        entity = Term.class,
                        parentColumns = "id",
                        childColumns = "term"
                ),
        },
        indices = {
                @Index(value = "id", unique = true),
                @Index(value ="term")
        }
)
public class Course {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "term")
    private int mTerm;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name= "startDate")
    private String mStartDate;

    @ColumnInfo(name = "endDate")
    private String mEndDate;

    @ColumnInfo(name = "status")
    private String mStatus;

    @ColumnInfo(name = "note")
    private String mNote;

    @ColumnInfo(name = "instructorName")
    private String mInstructorName;

    @ColumnInfo(name = "instructorPhone")
    private String mInstructorPhone;

    @ColumnInfo(name = "instructorEmail")
    private String mInstructorEmail;

    public Course(String name, int term, String startDate, String endDate, String status,
                  String instructorName, String instructorPhone, String instructorEmail, String note) {
        this.mName = name;
        this.mTerm = term;
        this.mStartDate = startDate;
        this.mEndDate = endDate;
        this.mStatus = status;
        this.mInstructorName = instructorName;
        this.mInstructorPhone = instructorPhone;
        this.mInstructorEmail = instructorEmail;
        this.mNote = note;
    }


    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getTerm() {
        return mTerm;
    }

    public void setTerm(int term) {
        this.mTerm = term;
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

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }

    public void setInstructorName(String instructorName) {
        this.mInstructorName = instructorName;
    }

    public String getInstructorName() {
        return mInstructorName;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.mInstructorPhone = instructorPhone;
    }

    public String getInstructorPhone() {
        return mInstructorPhone;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.mInstructorEmail = instructorEmail;
    }

    public String getInstructorEmail() {
        return mInstructorEmail;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        this.mNote = note;
    }

}
