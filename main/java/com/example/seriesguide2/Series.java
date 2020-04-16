package com.example.seriesguide2;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity
public class Series  implements Serializable {



    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "series name")
    private String series_name;

    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }

    @ColumnInfo(name = "poster")
    private String poster;
    @ColumnInfo(name = "genre")
    private String genre;
    @ColumnInfo(name = "rating")
    private float rating;
    @ColumnInfo(name = "pic")
    private byte pic[];
    @ColumnInfo(name = "omdbID")
    private String omdbID;
    @ColumnInfo(name = "numberSeason")
    private String season;

    public String getNewSeason() {
        return NewSeason;
    }

    public void setNewSeason(String newSeason) {
        NewSeason = newSeason;
    }

    private String NewSeason;

    public String getOmdbID() {
        return omdbID;
    }

    public void setOmdbID(String omdbID) {
        this.omdbID = omdbID;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getSeries_name() {
        return series_name;
    }

    public void setSeries_name(String series_name) {
        this.series_name = series_name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
