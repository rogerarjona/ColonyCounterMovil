package com.arjona.roger.test2.Entidades;

import android.content.Context;
import android.content.SharedPreferences;

import com.arjona.roger.test2.menu_app;

import static android.content.Context.MODE_PRIVATE;

public class Utils {

    public static String getUserName(){
        Context applicationContext = menu_app.getContextOfApplication();

        SharedPreferences prefs = applicationContext.getSharedPreferences("UserData", MODE_PRIVATE);
        String username = prefs.getString("username","");

        return username;
    }
}
