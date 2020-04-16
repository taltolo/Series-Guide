package com.example.seriesguide2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.content.Context;
import androidx.appcompat.app.AlertDialog;

import static com.example.seriesguide2.MainActivity.ConnectToInternet;
import static java.lang.System.exit;


public class InternetConnectionReceiver extends BroadcastReceiver {




    @Override
    public void onReceive(Context context, Intent intent) {
     if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            //check if there is no connection
            boolean noConnection = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            if (noConnection) {

                Toast.makeText(context, "You are not connected to the internet, Thus the notfication will not work.", Toast.LENGTH_SHORT).show();
                ConnectToInternet=false;
            }
            else {
                ConnectToInternet=true;
            }
        }
    }

}

