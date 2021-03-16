package kingwin.utils.gather;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.LinkedList;

import kingwin.utils.gather.common.ActivityUtils;
import kingwin.utils.gather.common.StringUtils;
import kingwin.utils.gather.common.UtilsActivityLifecycleImpl;

/**
 * KUtils引导器 (推荐以引导器为使用入口，引导器为最新目录调用)
 * @author KingWin
 * @since 2021/3/5 11:30 AM
 */

public class KUtilsGuide {

    public static void init(Application application){
        UtilsActivityLifecycleImpl.getInstance().init(application);
    }

    public static Context getContext(){
        Activity activity = getTopActivity();
        if(null != activity){
            return activity;
        }
        return UtilsActivityLifecycleImpl.getInstance().getApplication();
    }

    public static Application getApp(){
        return UtilsActivityLifecycleImpl.getInstance().getApplication();
    }

    public static Activity getTopActivity(){
        return UtilsActivityLifecycleImpl.getInstance().getTopActivity();
    }

    public static LinkedList<Activity> getActivityList(){
        return UtilsActivityLifecycleImpl.getInstance().getActivityList();
    }

    public static boolean isAppForeground(){
        return UtilsActivityLifecycleImpl.getInstance().isAppForeground();
    }

    public static Activity getActivityByContext(Context context){
        return ActivityUtils.getActivityByContext(context);
    }





    ///////////////////////////////////////////////////////////////////////////
    // StringUtils
    ///////////////////////////////////////////////////////////////////////////
    public static boolean isSpace(final String s) {
        return StringUtils.isSpace(s);
    }



    ///////////////////////////////////////////////////////////////////////////
    // RomUtils
    ///////////////////////////////////////////////////////////////////////////
    public static boolean isSamsung() {
        return true;
    }


}
