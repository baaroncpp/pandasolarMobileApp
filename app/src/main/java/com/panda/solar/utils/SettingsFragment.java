package com.panda.solar.utils;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.panda.solar.activities.R;

public class SettingsFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.pref_notifications);
    }
}
