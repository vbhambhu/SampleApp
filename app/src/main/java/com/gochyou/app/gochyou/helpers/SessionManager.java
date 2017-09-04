package com.gochyou.app.gochyou.helpers;


import java.util.HashMap;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.facebook.login.LoginManager;
import com.gochyou.app.gochyou.activities.WelcomeActivity;

public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "GochYouPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";


    public static final String KEY_UID = "uid";
    public static final String KEY_FBID = "fbid";
    public static final String KEY_NAME = "name";
    public static final String KEY_PROFILE_IMG = "profile_img";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(int uid, String fbid, String name, String image){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putInt(KEY_UID, uid);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_FBID, fbid);
        editor.putString(KEY_PROFILE_IMG, image);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){


        System.out.println("I am checking============");
        // Check login status
        if(!this.isLoggedIn()){

            System.out.println("===== logged in=========");
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, WelcomeActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_UID, pref.getString(KEY_UID, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        user.put(KEY_FBID, pref.getString(KEY_FBID, null));
        user.put(KEY_PROFILE_IMG, pref.getString(KEY_PROFILE_IMG, null));
        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logout(){

        //logout from fb
        LoginManager.getInstance().logOut();

        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, WelcomeActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public int getUserId(){
        return pref.getInt(KEY_UID, 0);
    }


}