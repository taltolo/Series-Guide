package com.example.seriesguide2;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Series.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SeriesDao seriesDao();


}
