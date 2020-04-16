package com.example.seriesguide2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import static com.example.seriesguide2.MainActivity.SeriesNewSeason;

public class NotificationUpdateSeason extends AppCompatActivity {
    public NotificationAdapter mAdapter;
    public RecyclerView rvUpdateSeason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_update_season);
        rvUpdateSeason = findViewById(R.id.rvUpdateSeason);
        mAdapter = new NotificationAdapter(this,SeriesNewSeason);
        rvUpdateSeason.setAdapter(mAdapter);
        rvUpdateSeason.setLayoutManager(new LinearLayoutManager(this));
    }
}
