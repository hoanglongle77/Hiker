package com.example.hikermanagementapp.Dao;
import com.example.hikermanagementapp.Models.Hike;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HikeDao {
    @Insert
    long insertHike(Hike hike);
    @Delete
    void deleteHike(Hike hike);
    @Update
    void updateHike(Hike hike);
    @Query("SELECT * FROM hikes ORDER BY name")
    List<Hike> getAllHikes();
}