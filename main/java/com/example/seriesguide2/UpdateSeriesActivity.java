package com.example.seriesguide2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import static com.example.seriesguide2.MainActivity.SeriesNewSeason;

public class UpdateSeriesActivity extends AppCompatActivity {

        private EditText edit_series_name,edit_SeasonsUpdate;
        private TextView generView2;
        private RatingBar ratingBar;
        private ImageView seriesPic;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_update_series);
            edit_series_name = findViewById(R.id.editTextSeries);
            edit_SeasonsUpdate=findViewById(R.id.edit_SeasonsUpdate);
            generView2 = findViewById(R.id.generView2);
            ratingBar = findViewById(R.id.ratingBar);
            seriesPic=findViewById(R.id.imageViewUpload);
            final Series series = (Series) getIntent().getSerializableExtra("series");
            loadSeries(series);

            findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateSeries(series);
                }
            });

            findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateSeriesActivity.this);
                    builder.setTitle("Are you sure?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for (int j = 0; j <SeriesNewSeason.size() ; j++) {
                                Series s= (Series) SeriesNewSeason.get(j);
                                if (s.getOmdbID().equals(series.getOmdbID())){
                                    SeriesNewSeason.remove(j);
                                    break;
                                }
                            }
                            deleteSeries(series);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    AlertDialog ad = builder.create();
                    ad.show();
                }
            });
        }



    protected void onActivityResult(int restCode,int resultCode,Intent Data){
        super.onActivityResult(restCode,resultCode,Data);
    }

        private void loadSeries(Series series) {
            edit_series_name.setText(series.getSeries_name());
            generView2.setText("Gener : "+series.getGenre());
            edit_SeasonsUpdate.setText(series.getSeason());
            ratingBar.setRating(series.getRating());
            Picasso.get().load(series.getPoster()).into(seriesPic);

        }

        private void updateSeries(final Series series) {
            final String series_name = edit_series_name.getText().toString().trim();
            final float rating = ratingBar.getRating();
            final String SeasonUpdate=edit_SeasonsUpdate.getText().toString();


            if (series_name.isEmpty()) {
                edit_series_name.setError("Series name required");
                edit_series_name.requestFocus();
                return; }

            if (SeasonUpdate.isEmpty()) {
                edit_SeasonsUpdate.setError("Season number required");
                edit_SeasonsUpdate.requestFocus();
                return; }


            if (rating==0.0) {
                ratingBar.setRating(0.0f);
                return; }

            class UpdateSeries extends AsyncTask<Void, Void, Void> {
                @Override
                protected Void doInBackground(Void... voids) {
                    series.setSeries_name(series_name);
                    series.setRating(rating);
                    series.setSeason(SeasonUpdate);
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .seriesDao()
                            .update(series);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Intent i=new Intent(UpdateSeriesActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }
            UpdateSeries ut = new UpdateSeries();
            ut.execute();
        }

        private void deleteSeries(final Series series) {
            class DeleteTask extends AsyncTask<Void, Void, Void> {
                @Override
                protected Void doInBackground(Void... voids) {
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .seriesDao()
                            .delete(series);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Intent i=new Intent(UpdateSeriesActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }

            DeleteTask dt = new DeleteTask();
            dt.execute();
        }
}
