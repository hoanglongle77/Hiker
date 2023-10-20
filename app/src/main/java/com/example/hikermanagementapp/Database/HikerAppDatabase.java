package com.example.hikermanagementapp.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.hikermanagementapp.Dao.HikeDao;
import com.example.hikermanagementapp.Models.Hike;

@Database(entities = {Hike.class}, version = 1)
public abstract class HikerAppDatabase extends RoomDatabase {
    public abstract HikeDao hikeDao();
}
