package com.example.seriesguide2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;

public class AddSeriesActivity extends AppCompatActivity implements Serializable {
       static OmdbWebServiceClient omdbWebServiceClient;


    String apiKey="2c990743";
    private EditText edit_series_name,edit_Seasons;
        private RatingBar ratingBar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_series);

            edit_series_name = findViewById(R.id.edit_series_name);
            edit_Seasons=findViewById(R.id.edit_Seasons);
            ratingBar = findViewById(R.id.ratingBar);


            findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addSeries();
                }
            });
        }

    protected void onActivityResult(int restCode,int resultCode,Intent Data){
        super.onActivityResult(restCode,resultCode,Data);

        }

        private void addSeries () {
            final String series_name = edit_series_name.getText().toString().trim();
            final float rating = ratingBar.getRating();
            final String Seasons = edit_Seasons.getText().toString().trim();
            final String imdbID;


            String jsonResponse = OmdbWebServiceClient.searchMovieByTitle("" + series_name, "" + apiKey);
            String SerachJSON, imdbIDJSON = "";
            try {

                JSONObject series = new JSONObject(jsonResponse);
                SerachJSON = series.getString("Search");
                SerachJSON = SerachJSON.substring(1);
                series = new JSONObject(SerachJSON);
                imdbIDJSON = series.getString("imdbID");

            } catch (JSONException e) {
                e.printStackTrace();
            }


            imdbID = imdbIDJSON;


            String jsonResponseID = OmdbWebServiceClient.searchMovieByImdb("" + imdbID, "" + apiKey);
            String Genar = "";
            String posterJSON = "";
            try {
                JSONObject series2 = new JSONObject(jsonResponseID);
                posterJSON = series2.getString("Poster");
                Genar = series2.getString("Genre");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String poster = posterJSON;
            final String genre = Genar;

            if (series_name.isEmpty()) {
                edit_series_name.setError("Series name required");
                edit_series_name.requestFocus();
                return;
            }
            if (Seasons.isEmpty()) {
                edit_Seasons.setError("Seasons number is required");
                edit_Seasons.requestFocus();

            }

            if (rating == 0.0) {
                ratingBar.setRating(0.0f);
                return;
            }


            class SaveSeries extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {

                    //creating a series
                    Series series = new Series();
                    series.setSeries_name(series_name);
                    series.setGenre(genre);
                    series.setRating(rating);
                    series.setPoster(poster);
                    series.setSeason(Seasons);
                    series.setOmdbID(imdbID);


                    //adding to database
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .seriesDao()
                            .insert(series);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Intent i = new Intent(AddSeriesActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }

            SaveSeries st = new SaveSeries();
            st.execute();


        }


}
