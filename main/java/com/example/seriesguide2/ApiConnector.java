package com.example.seriesguide2;
import android.content.Context;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class ApiConnector  {
      ArrayList <String>S=new ArrayList<>();

    public static  final String SEARCH_URL="http://www.omdbapi.com/?s=TITLE&type=series&apikey=2c990743";
    public static final String SEARCH_BY_IMDB_URL="http://www.omdbapi.com/?i=IMDB&type=series&apikey=2c990743";

public static String SearchSeriesByName(final Context context, String title){


     final String[] imdbIDtemp = {""};
    RequestQueue queue = Volley.newRequestQueue(context);
    StringRequest request =new StringRequest(Request.Method.GET, SEARCH_URL
            .replaceAll("TITLE",title),
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    String SerachJSON,imdbID;
                       System.out.println(response);
                    try {
                        JSONObject series=new JSONObject(response);
                        String result=series.getString("Response");
                        SerachJSON= series.getString("Search");
                        SerachJSON=SerachJSON.substring(1);
                        series=new JSONObject(SerachJSON);
                        imdbID=series.getString("imdbID");
                        if (result.equals("True")){
                            imdbIDtemp[0] =imdbID;
                            SearchSeriesByID(context,imdbID);
                           Toast.makeText(context,imdbID,Toast.LENGTH_LONG).show();


                        }
                        else{
                            Toast.makeText(context,"Series not exist",Toast.LENGTH_LONG).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            },
            new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {

                }



            });
    queue.add(request);
return imdbIDtemp[0];


}

public static void  SearchSeriesByID(final Context context, String imdbID){

    final ArrayList <String>S=new ArrayList<>();
    RequestQueue queue = Volley.newRequestQueue(context);
    StringRequest request =new StringRequest(Request.Method.GET, SEARCH_BY_IMDB_URL
            .replaceAll("IMDB",imdbID),
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    String Genar="";
                    String posterJSON="";
                    System.out.println(response);
                    try {
                        JSONObject series=new JSONObject(response);
                        String result=series.getString("Response");
                        posterJSON= series.getString("Poster");
                        Genar=series.getString("Genre");
                        if (result.equals("True")){
                          S.add(posterJSON);
                          S.add(Genar);
                            Toast.makeText(context,""+posterJSON+""+Genar,Toast.LENGTH_LONG).show();


                        }
                        else{
                            Toast.makeText(context,"Series not exist",Toast.LENGTH_LONG).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            },
            new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {

                }



            });
    queue.add(request);



}

}