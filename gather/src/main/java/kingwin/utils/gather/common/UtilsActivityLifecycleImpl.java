package kingwin.utils.gather.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

/**
 * ActivityLifecycle 实现工具
 * @author KingWin
 * @since 2021/3/5 4:08 PM
 */

public class UtilsActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {



    private static volatile UtilsActivityLifecycleImpl mInstance;

    /**
     * 获取单例对象
     * @return ContextManage对象
     */
    public static UtilsActivityLifecycleImpl getInstance(){
        if(null == mInstance){
            synchronized (UtilsActivityLifecycleImpl.class){
                if(null == mInstance){
                    mInstance = new UtilsActivityLifecycleImpl();
                }
            }
        }
        return mInstance;
    }


    private LinkedList<Activity> mActivityList;

    private Application mApplication;

    private int     mForegroundCount = 0;
    private int     mConfigCount     = 0;
    private boolean mIsBackground    = false;

    /**
     * 初始化（注册生命周期监听）
     * @param application
     */
    public void init(Application application){
        mApplication = application;
        application.registerActivityLifecycleCallbacks(this);
    }

    /**
     * 获取Application
     * @return Application
     */
    public Application getApplication(){
        if(null == mApplication){
            init(ActivityUtils.getApplicationByReflect());
            if(null == mApplication){
                throw new NullPointerException("reflect failed.");
            }
        }
        return mApplication;
    }


    /**
     * 设置顶部Activity
     * @param activity
     */
    public void setTopActivity(Activity activity){
        if(null == mActivityList){
            mActivityList = new LinkedList<>();
        }
        if(mActivityList.contains(activity)){
            if(mActivityList.getFirst().equals(activity)){
                mActivityList.remove(activity);
                mActivityList.addFirst(activity);
            }
        }else{
            mActivityList.addFirst(activity);
        }

    }


    /**
     * 获取顶部Activity
     * @return Activity
     */
    public Activity getTopActivity(){
       List<Activity> activityList = getActivityList();
        for (Activity activity : activityList) {
            if(!ActivityUtils.isActivityAlive(activity)){
                continue;
            }
            return activity;
        }
        return null;
    }


    /**
     * 获取App Activity列表
     * @return LinkedList<Activity>
     */
    public LinkedList<Activity> getActivityList(){
        if(null != mActivityList && !mActivityList.isEmpty()){
            return new LinkedList<>(mActivityList);
        }
        List<Activity> reflectActivities = ActivityUtils.getActivitiesByReflect();
        mActivityList.addAll(reflectActivities);
        return new LinkedList<>(mActivityList);

    }


    public boolean isAppForeground() {
        return !mIsBackground;
    }





    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        setTopActivity(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if (!mIsBackground) {
            setTopActivity(activity);
        }
        if (mConfigCount < 0) {
            ++mConfigCount;
        } else {
            ++mForegroundCount;
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        setTopActivity(activity);
        if (mIsBackground) {
            mIsBackground = false;
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        if (activity.isChangingConfigurations()) {
            --mConfigCount;
        } else {
            --mForegroundCount;
            if (mForegroundCount <= 0) {
                mIsBackground = true;
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        mActivityList.remove(activity);
    }


}
