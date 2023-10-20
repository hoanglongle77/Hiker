package com.example.hikermanagementapp.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "hikes")
public class Hike {
    @PrimaryKey(autoGenerate = true)
    private long hikeId;
    private String name;
    private String location;
    private Date date;
    private boolean parkingAvailable;
    private double length;
    private String difficulty;
    private String description;


}
