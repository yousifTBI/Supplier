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

    public void setSharedDistributor_ID(String number) {
        edits.putString("Distributor_ID", number);
        edits.apply();
    }
    public void setSharedUser_ID(String number) {
        edits.putString("User_ID", number);
        edits.apply();
    }


    public void setSerial_ID(String number) {
        edits.putString("Serial_ID", number);
        edits.apply();
    }



    public String gerSharedDistributor_ID() {
        String phoneNumber = sharedPreferencesLogIn.getString("Distributor_ID", "");
        return phoneNumber;

    }
    public String gerSharedUser_ID() {
        String phoneNumber = sharedPreferencesLogIn.getString("User_ID", "");
        return phoneNumber;

    }

 public String getSerial_ID() {
        String phoneNumber = sharedPreferencesLogIn.getString("Serial_ID", "");
        return phoneNumber;

    }

}