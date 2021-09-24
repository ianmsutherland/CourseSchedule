package com.ianmsutherland.ianmsutherlandscheduler.Entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "instructors"
        ,indices = {@Index(value={"id"},unique = true)}
        )
public class Instructor {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "phoneNumber")
    private String mPhoneNumber;

    @ColumnInfo(name = "email")
    private String mEmail;

    public Instructor(int id, String name, String phoneNumber, String email) {
        this.mId = id;
        this.mName = name;
        this.mPhoneNumber = phoneNumber;
        this.mEmail = email;
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

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.mPhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return mEmail  ;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

}
