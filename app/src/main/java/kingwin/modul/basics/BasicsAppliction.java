package kingwin.modul.basics;

import android.app.Application;

import kingwin.modul.logger.KLogger;

/**
 * author: kingwin
 * created on: 2020/12/11 5:45 PM
 * description:
 */
public class BasicsAppliction extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        KLogger.init();
    }
}
