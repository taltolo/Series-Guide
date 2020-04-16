package com.example.seriesguide2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SeriesDao {

    @Query("SELECT * FROM series")
    List<Series> getAll();

    @Insert
    void insert(Series series);

    @Delete
    void delete(Series series);

    @Update
    void update(Series series);


}
