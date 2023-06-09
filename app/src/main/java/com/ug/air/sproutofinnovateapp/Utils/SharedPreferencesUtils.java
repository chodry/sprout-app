package com.ug.air.sproutofinnovateapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {

    public static boolean isSharedPreferencesFileExists(Context context, String fileName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getAll().size() > 0;
    }

}
