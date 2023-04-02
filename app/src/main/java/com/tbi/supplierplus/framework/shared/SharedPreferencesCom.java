package com.tbi.supplierplus.framework.shared;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesCom {
    private static SharedPreferencesCom Instance;
    private SharedPreferences sharedPreferencesLogIn;
    private SharedPreferences.Editor edits;

    public SharedPreferencesCom() {
    }

    public static void init(Context context) {
        Instance = new SharedPreferencesCom(context.getApplicationContext());

    }

    public static SharedPreferencesCom getInstance() {
        if (null == Instance)
            Instance = new SharedPreferencesCom();
        return Instance;
    }

    public SharedPreferencesCom(Context context) {
        sharedPreferencesLogIn = context.getSharedPreferences("Login", MODE_PRIVATE);
        edits = sharedPreferencesLogIn.edit();
    }

    public void remove() {
        sharedPreferencesLogIn.edit().clear().commit();
    }

    public void setSharedphoneNumber(String number
    ) {
        edits.putString("Distributor_ID", number);
        edits.apply();
    }



    public String gerSharedphoneNumber(
    ) {
        String phoneNumber = sharedPreferencesLogIn.getString("Distributor_ID", "");
        return phoneNumber;

    }


}