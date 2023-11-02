package com.example.hikermanagementapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.hikermanagementapp.Dao.HikeDao;
import com.example.hikermanagementapp.Models.Hike;

@Database(entities = {Hike.class}, version = 1)
public abstract class HikerAppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "hiker.db";
    private static HikerAppDatabase instance;

    public static synchronized HikerAppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), HikerAppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract HikeDao hikeDao();
}
