package com.wiser.tantan;

import android.app.Application;

import com.wiser.library.helper.WISERHelper;

public class TTApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        WISERHelper.newBind().Inject(this, BuildConfig.DEBUG);
    }
}
