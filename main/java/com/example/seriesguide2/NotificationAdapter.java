package com.example.seriesguide2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context mCtx;
    private List<Series> seriesList;

    public NotificationAdapter(Context mCtx, List<Series> seriesList) {
            this.mCtx = mCtx;
            this.seriesList = seriesList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View notificationView = inflater.inflate(R.layout.row_updateseason, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(notificationView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    Series series = seriesList.get(position);
    int season=Integer.parseInt(series.getNewSeason())-Integer.parseInt(series.getSeason());
    holder.series_name.setText(series.getSeries_name());
    holder.UpdateSeason_name.setText("There are "+season+" seasons you haven't watched yet ("+series.getSeason()+"/"+series.getNewSeason()+")");
    Picasso.get().load(series.getPoster()).into(holder.seriesImage);
    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView UpdateSeason_name;
        public TextView series_name;
        public ImageView seriesImage;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            UpdateSeason_name = (TextView) itemView.findViewById(R.id.UpdateSeason_text);
            series_name = (TextView) itemView.findViewById(R.id.series_name);
            seriesImage = (ImageView) itemView.findViewById(R.id.imageView);

        }
    }
}
