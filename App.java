package com.example.tyler.happyhour;

import android.app.Application;
import android.content.Context;

/**
 * Created by tyler on 4/29/2018.
 */

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
