package kingwin.modul.logger;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * author: kingwin
 * created on: 2020/12/11 5:21 PM
 * description:
 */
public class LoggerAppliction extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        KLogger.init();
    }
}
