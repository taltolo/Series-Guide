package com.example.seriesguide2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

public class MySettingsFragment extends PreferenceFragmentCompat {

    public static final String SAVE_KEY = "save";
    public static final String METHOD = "savingMethod";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /* Switch text for switches*/

        SwitchPreferenceCompat switchPref;
        switchPref = (SwitchPreferenceCompat)  getPreferenceManager().findPreference(METHOD);
        switchPref.setSummaryOff("set as Grid");
        switchPref.setSummaryOn("set as Row");

        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
