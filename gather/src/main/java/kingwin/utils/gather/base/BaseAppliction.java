package kingwin.utils.gather.base;

import android.app.Application;

import kingwin.utils.gather.common.UtilsActivityLifecycleImpl;

/**
 * @author KingWin
 * @since 2021/3/5 2:37 PM
 */

public class BaseAppliction extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UtilsActivityLifecycleImpl.getInstance().init(this);
    }
}
