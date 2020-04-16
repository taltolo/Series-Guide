package com.example.seriesguide2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import static com.example.seriesguide2.MySettingsFragment.METHOD;


public class MainActivity extends AppCompatActivity implements  ExitDialog.OnExitDialogListener {
    public static final String MYTAG = "MYTAG";
    public static String METHODTOSP = "";
    public static List SeriesNewSeason=new ArrayList();
    private RecyclerView recyclerView;
    SeriesAdapter adapter;
    public static boolean ConnectToInternet=true;

    BroadcastReceiver myBroadcastReceiver = new InternetConnectionReceiver();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //IntertetConnection Receiver
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myBroadcastReceiver, intentFilter);
        //
        getMETHODFromFile();
        String im= ApiConnector.SearchSeriesByName(this,"mad men");
        Toast.makeText(this,im,Toast.LENGTH_LONG).show();



        if (METHODTOSP.equals("ROW")){
            recyclerView = findViewById(R.id.rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        }
        else {
            recyclerView = findViewById(R.id.rv);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddSeriesActivity.class);
                startActivity(intent);
            }
        });
        getSeries();
    }

    private static final String SERIES = "series";


    /* Save into SP*/
    public String getMETHODFromFile() {
        boolean filterRemovedCountries =  PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(METHOD, false);
        if (filterRemovedCountries) {
            METHODTOSP="ROW";
            //
        }
        else {
            METHODTOSP="GRID";
        }

        String METHOD_TO_RV = METHODTOSP;

        try {

                    Log.i(MYTAG, "***************Reading removed METHOD from SP");
                    Context context = this;
                    SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                    METHOD_TO_RV = sharedPref.getString(SERIES, METHODTOSP);
                    return METHOD_TO_RV;




        } catch (Exception e) {
            Log.e(MYTAG, e.getMessage());
        }
        return METHOD_TO_RV;
    }


    public void saveMETHOD() {

        try {
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(SERIES, METHODTOSP);
            editor.apply();
        } catch (Exception e) {
            Log.e(MYTAG, e.getMessage());
        }
    }



    @Override
    public void onExitpress() {

        finish();
        System.exit(0);
    }

    public void startService(List<Series> series) {


        Intent serviceIntent = new Intent(this, MySeasonService.class);
        for (int i = 0; i < series.size(); i++) {

            serviceIntent.putExtra("series",series.get(i) );

        ContextCompat.startForegroundService(this, serviceIntent);
        }

    }



    @Override
    public void onDestroy() {


        saveMETHOD();
        super.onDestroy();
    }


    private void getSeries() {
        class GetSeries extends AsyncTask<Void, Void, List<Series>> {

            @Override
            protected List<Series> doInBackground(Void... voids) {
                List<Series> seriesList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .seriesDao()
                        .getAll();
                return seriesList;
            }

            @Override
            protected void onPostExecute(List<Series> series) {
                super.onPostExecute(series);
                adapter = new SeriesAdapter(MainActivity.this, series);
                if(ConnectToInternet)
                    startService(series);
                recyclerView.setAdapter(adapter);
            }
        }

        GetSeries gt = new GetSeries();
        gt.execute();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            recyclerView.setAdapter(adapter);
            Log.d(this.getClass().getName(), "back button pressed");
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.action_settings:
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                recyclerView.setAdapter(null);
                ft.add(R.id.settings_container, new MySettingsFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.action_Notification:
                Intent NotificationIntent=new Intent(MainActivity.this,NotificationUpdateSeason.class);
                startActivity(NotificationIntent);
                return false;

            case R.id.Exit:
                ExitDialog exitDialog=new ExitDialog();
                exitDialog.show((getSupportFragmentManager()),"Exitdialog");
                return true;

            default:
                return super.onOptionsItemSelected(item);


        }

    }









}
