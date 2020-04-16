package com.example.seriesguide2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.Set;
import static com.example.seriesguide2.MainActivity.METHODTOSP;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {
    static Activity mContext;
    private Context mCtx;
    private List<Series> seriesList;

    //Constructor
    public SeriesAdapter(Context mCtx, List<Series> seriesList) {
        this.mCtx = mCtx;
        this.seriesList = seriesList;
        mContext= (Activity) mCtx;
    }

    @NonNull
    @Override
    public SeriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        if(METHODTOSP.equals("GRID")){
            System.out.println("GRID FROM ADATPER***************");
        View seriesView = inflater.inflate(R.layout.row_series, parent, false);
            ViewHolder viewHolder = new ViewHolder(seriesView);
            return viewHolder;
        }

         else{
            System.out.println("RRRROW FROM ADATPER***************");
            View seriesView = inflater.inflate(R.layout.rowlist_series, parent, false);
            ViewHolder viewHolder = new ViewHolder(seriesView);
            return viewHolder;
        }
        // Return a new holder instance

    }

        @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Series series = seriesList.get(position);
            holder.SeriesName.setText(series.getSeries_name());
            holder.Genre.setText(series.getGenre());
            holder.ratingBar.setRating(series.getRating());
            Picasso.get().load(series.getPoster()).into(holder.SeriesImage);
    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView SeriesName;
        public TextView Genre;
        public RatingBar ratingBar;
        public ImageView SeriesImage;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            SeriesName = (TextView) itemView.findViewById(R.id.series_name);
            Genre = (TextView) itemView.findViewById(R.id.genre);
            ratingBar=(RatingBar)itemView.findViewById(R.id.ratingBar);
            SeriesImage=(ImageView)itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener((View.OnClickListener) this);

        }

        @Override
        public void onClick(View view) {
            Series series = seriesList.get(getAdapterPosition());
            Intent intent = new Intent(mCtx, UpdateSeriesActivity.class);
            intent.putExtra("series", series);
            mCtx.startActivity(intent);
        }
    }
}