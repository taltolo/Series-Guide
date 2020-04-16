package com.example.seriesguide2;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import static com.example.seriesguide2.MainActivity.SeriesNewSeason;

import static com.example.seriesguide2.App.CHANNEL_ID;
public class MySeasonService extends IntentService implements Serializable {

    private static final String TAG = "MySeasonService";


    private PowerManager.WakeLock wakeLock;

    public MySeasonService() {
        super("MySeasonService");
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        Log.d(TAG, "Wakelock acquired");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("series guide")
                    .setContentText("Running...")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .build();

            startForeground(1, notification);
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "onHandleIntent");
        Boolean equal=false;
        final Series series = (Series) intent.getSerializableExtra("series");

        String jsonResponseID=OmdbWebServiceClient.searchMovieByImdb(""+series.getOmdbID(),"2c990743");
        String totalSeasons="";
        try {
            JSONObject seriesJSONO = new JSONObject(jsonResponseID);
             totalSeasons= seriesJSONO.getString("totalSeasons");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
            for(int i=0;i< SeriesNewSeason.size();i++)
            {
                Series s= (Series) SeriesNewSeason.get(i);
                System.out.println(s.getSeries_name()+"**FROM SERVICE");
                if(s.getOmdbID().equals(series.getOmdbID())){
                    System.out.println("ALREADY IN LIST");
                    equal=true;

                    if(!series.getSeason().equals(s.getSeason()))
                        s.setSeason(series.getSeason());

                    if (series.getSeason().equals(totalSeasons))
                        SeriesNewSeason.remove(i);
                }
            }
            if(!equal) {
                if (!(series.getSeason().equals(totalSeasons)))
                {
                    series.setNewSeason(totalSeasons);
                    SeriesNewSeason.add(series);
                }
            }

            Log.i(TAG, "**************** "+ series.getSeries_name() + "The new Seasons : "+totalSeasons +" The Last subForSeasons you watch: "+series.getSeason());




    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

    }


}
