package com.incode.incode;

import android.app.Application;

import java.io.File;

/**
 * Created by kuri on 6/6/17.
 */

public class IncodeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        clearCache();
    }

    /**
     * Clear cache every time we start the app
     */
    private void clearCache(){
        File cacheDirectory = new File(getApplicationContext().getExternalFilesDir(null), "Cache");
        if(cacheDirectory.exists() && cacheDirectory.isDirectory()){
            for(File f : cacheDirectory.listFiles()){
                f.delete();
            }
        }
    }
}
