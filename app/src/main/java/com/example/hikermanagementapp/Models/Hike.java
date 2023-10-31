package com.example.hikermanagementapp.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "hikes")
public class Hike {
    @PrimaryKey(autoGenerate = true)
    public long hikeId;
    public String name;
    public String location;
    public String date;
    public String parkingAvailable;
    public int length;
    public String difficulty;
    public String description;
}
