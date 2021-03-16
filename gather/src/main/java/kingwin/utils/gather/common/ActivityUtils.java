package kingwin.utils.gather.common;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kingwin.utils.gather.KUtilsGuide;


/**
 * Activity工具
 * @author KingWin
 * @since 2021/3/3 6:08 PM
 */
public final class ActivityUtils {

    private ActivityUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 添加Activity生命周期监听（废除）
     *
     * @deprecated Activity生命周期统一由Appliction监听，所以暂时废除此接口
     *
     */
    public static void addActivityLifecycleCallbacks() {
    }



    /**
     * 移除Activity生命周期监听（废除）
     *
     * @deprecated Activity生命周期统一由Appliction监听，所以暂时废除此接口
     *
     */
    public static void removeActivityLifecycleCallbacks() {
    }



    /**
     * 通过Context返回Activity
     *
     * @param context Context上下文
     * @return context对应的Activity
     */
    @Nullable
    public static Activity getActivityByContext(@NonNull Context context) {
        Activity activity = getActivityByContextInner(context);
        if (!isActivityAlive(activity)) return null;
        return activity;
    }

    /**
     * 根据context对象类型返回Activity
     *
     * @param context Context上下文
     * @return context对应的Activity
     */
    @Nullable
    private static Activity getActivityByContextInner(@Nullable Context context) {
        if (context == null) return null;
        List<Context> list = new ArrayList<>();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            Activity activity = getActivityFromDecorContext(context);
            if (activity != null) return activity;
            list.add(context);
            context = ((ContextWrapper) context).getBaseContext();
            if (context == null) {
                return null;
            }
            if (list.contains(context)) {
                // loop context
                return null;
            }
        }
        return null;
    }

    /**
     * 通过反射DecorContext获取Activity
     * 已不再使用反射DecorContext的mActivityContext（7.0之后mActivityContext已不存在）
     * 反射DecorContext里的mPhoneWindow对象的getContext方法尝试获取Context的Activity
     *
     * @param context Context上下文
     * @return context对应的Activity
     */
    @Nullable
    private static Activity getActivityFromDecorContext(@Nullable Context context) {
        if (context == null) return null;
        if (context.getClass().getName().equals("com.android.internal.policy.DecorContext")) {
            try {
                Field phoneWindowField = context.getClass().getDeclaredField("mPhoneWindow");
                phoneWindowField.setAccessible(true);
                Object obj =  phoneWindowField.get(context);
                Method activityContext = obj.getClass().getMethod("getContext");
                return ((Activity) activityContext.invoke(obj));
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    /**
     * Activity是否存在
     *
     * @param pkg 包名
     * @param cls 类名
     * @return true ==> 存在  false ==> 不存在
     */
    public static boolean isActivityExists(@NonNull final String pkg,
                                           @NonNull final String cls) {
        Intent intent = new Intent();
        intent.setClassName(pkg, cls);
        PackageManager pm = KUtilsGuide.getApp().getPackageManager();
        return !(pm.resolveActivity(intent, 0) == null ||
                intent.resolveActivity(pm) == null ||
                pm.queryIntentActivities(intent, 0).size() == 0);
    }

    /**
     * 启动Activity
     *
     * @param clz 需要被打开的Activity类
     */
    public static void startActivity(@NonNull final Class<? extends Activity> clz) {
        Context context = getTopActivityOrApp();
        startActivity(context, null, context.getPackageName(), clz.getName(), null);
    }

    /**
     * 启动Activity
     *
     * @param clz 需要被打开的Activity类
     * @param options 参数
     */
    public static void startActivity(@NonNull final Class<? extends Activity> clz,
                                     @Nullable final Bundle options) {
        Context context = getTopActivityOrApp();
        startActivity(context, null, context.getPackageName(), clz.getName(), options);
    }

    /**
     * 启动Activity
     *
     * @param clz 需要被打开的Activity类
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static void startActivity(@NonNull final Class<? extends Activity> clz,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        Context context = getTopActivityOrApp();
        startActivity(context, null, context.getPackageName(), clz.getName(),
                getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 启动Activity
     *
     * @param activity 当前的Activity
     * @param clz 需要被打开的Activity类
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(), null);
    }

    /**
     * 启动Activity
     *
     * @param activity 当前的Activity
     * @param clz 需要被打开的Activity类
     * @param options 参数
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz,
                                     @Nullable final Bundle options) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(), options);
    }

    /**
     * 启动Activity
     *
     * @param activity 当前的Activity
     * @param clz 需要被打开的Activity类
     * @param sharedElements 要传输给被调用活动的共享元素的名称及其关联视图(<API:21 无效)
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz,
                                     final View... sharedElements) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(),
                getOptionsBundle(activity, sharedElements));
    }

    /**
     * 启动Activity
     *
     * @param activity 当前的Activity
     * @param clz 需要被打开的Activity类
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(),
                getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }


    /**
     * 启动Activity
     *
     * @param pkg 包名
     * @param cls 类名
     */
    public static void startActivity(@NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(getTopActivityOrApp(), null, pkg, cls, null);
    }

    /**
     * 启动Activity
     *
     * @param pkg 包名
     * @param cls 类名
     * @param options 参数
     */
    public static void startActivity(@NonNull final String pkg,
                                     @NonNull final String cls,
                                     @Nullable final Bundle options) {
        startActivity(getTopActivityOrApp(), null, pkg, cls, options);
    }

    /**
     * 启动Activity
     *
     * @param pkg 包名
     * @param cls 类名
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static void startActivity(@NonNull final String pkg,
                                     @NonNull final String cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        Context context = getTopActivityOrApp();
        startActivity(context, null, pkg, cls, getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 启动Activity
     *
     * @param activity 当前的Activity
     * @param pkg 包名
     * @param cls 类名
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(activity, null, pkg, cls, null);
    }

    /**
     * 启动Activity
     *
     * @param activity 当前的Activity
     * @param pkg 包名
     * @param cls 类名
     * @param options 参数
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @Nullable final Bundle options) {
        startActivity(activity, null, pkg, cls, options);
    }

    /**
     * 启动Activity
     *
     * @param activity 当前的Activity
     * @param pkg 包名
     * @param cls 类名
     * @param sharedElements 要传输给被调用活动的共享元素的名称及其关联视图(<API:21 无效)
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     final View... sharedElements) {
        startActivity(activity, null, pkg, cls, getOptionsBundle(activity, sharedElements));
    }

    /**
     * 启动Activity
     *
     * @param activity 当前的Activity
     * @param pkg 包名
     * @param cls 类名
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(activity, null, pkg, cls, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }


    /**
     * 启动Activity
     *
     * @param intent 要启动的活动的描述
     */
    public static boolean startActivity(@NonNull final Intent intent) {
        return startActivity(intent, getTopActivityOrApp(), null);
    }

    /**
     * 启动Activity
     *
     * @param intent 要启动的活动的描述
     * @param options 参数
     */
    public static boolean startActivity(@NonNull final Intent intent,
                                        @Nullable final Bundle options) {
        return startActivity(intent, getTopActivityOrApp(), options);
    }

    /**
     * 启动Activity
     *
     * @param intent 要启动的活动的描述
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static boolean startActivity(@NonNull final Intent intent,
                                        @AnimRes final int enterAnim,
                                        @AnimRes final int exitAnim) {
        Context context = getTopActivityOrApp();
        boolean isSuccess = startActivity(intent, context, getOptionsBundle(context, enterAnim, exitAnim));
        if (isSuccess) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
                ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
            }
        }
        return isSuccess;
    }

    /**
     * 启动Activity
     *
     * @param activity 当前的Activity
     * @param intent 要启动的活动的描述
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Intent intent) {
        startActivity(intent, activity, null);
    }

    /**
     * 启动Activity
     *
     * @param activity 当前的Activity
     * @param intent 要启动的活动的描述
     * @param options 参数
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Intent intent,
                                     @Nullable final Bundle options) {
        startActivity(intent, activity, options);
    }

    /**
     * 启动Activity
     *
     * @param activity 当前的Activity
     * @param intent 要启动的活动的描述
     * @param sharedElements 要传输给被调用活动的共享元素的名称及其关联视图(<API:21 无效)
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Intent intent,
                                     final View... sharedElements) {
        startActivity(intent, activity, getOptionsBundle(activity, sharedElements));
    }

    /**
     * 启动Activity
     *
     * @param activity 当前的Activity
     * @param intent 要启动的活动的描述
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Intent intent,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(intent, activity, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }
    /**
     * 启动Activity（带返回结果）
     *
     * @param activity 当前的Activity
     * @param clz 需要被打开的Activity类
     * @param requestCode 请求码
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode) {
        startActivityForResult(activity, null, activity.getPackageName(), clz.getName(),
                requestCode, null);
    }

    /**
     * 启动Activity（带返回结果）
     *
     * @param activity 当前的Activity
     * @param clz 需要被打开的Activity类
     * @param requestCode 请求码
     * @param options 参数
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              @Nullable final Bundle options) {
        startActivityForResult(activity, null, activity.getPackageName(), clz.getName(),
                requestCode, options);
    }

    /**
     * 启动Activity（带返回结果）
     *
     * @param activity 当前的Activity
     * @param clz 需要被打开的Activity类
     * @param requestCode 请求码
     * @param sharedElements 要传输给被调用活动的共享元素的名称及其关联视图(<API:21 无效)
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              final View... sharedElements) {
        startActivityForResult(activity, null, activity.getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(activity, sharedElements));
    }

    /**
     * 启动Activity（带返回结果）
     *
     * @param activity 当前的Activity
     * @param clz 需要被打开的Activity类
     * @param requestCode 请求码
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              @AnimRes final int enterAnim,
                                              @AnimRes final int exitAnim) {
        startActivityForResult(activity, null, activity.getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }


    /**
     * 启动Activity（带返回结果）
     *
     * @param activity 当前的Activity
     * @param intent 要启动的活动的描述
     * @param requestCode 请求码
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Intent intent,
                                              final int requestCode) {
        startActivityForResult(intent, activity, requestCode, null);
    }

    /**
     * 启动Activity（带返回结果）
     *
     * @param activity 当前的Activity
     * @param intent 要启动的活动的描述
     * @param requestCode 请求码
     * @param options 参数
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Intent intent,
                                              final int requestCode,
                                              @Nullable final Bundle options) {
        startActivityForResult(intent, activity, requestCode, options);
    }

    /**
     * 启动Activity（带返回结果）
     *
     * @param activity 当前的Activity
     * @param intent 要启动的活动的描述
     * @param requestCode 请求码
     * @param sharedElements 要传输给被调用活动的共享元素的名称及其关联视图(<API:21 无效)
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Intent intent,
                                              final int requestCode,
                                              final View... sharedElements) {
        startActivityForResult(intent, activity,
                requestCode, getOptionsBundle(activity, sharedElements));
    }

    /**
     * 启动Activity（带返回结果）
     *
     * @param activity 当前的Activity
     * @param intent 要启动的活动的描述
     * @param requestCode 请求码
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Intent intent,
                                              final int requestCode,
                                              @AnimRes final int enterAnim,
                                              @AnimRes final int exitAnim) {
        startActivityForResult(intent, activity,
                requestCode, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 启动Activity（带返回结果）
     *
     * @param fragment 当前的Fragment
     * @param clz 需要被打开的Activity类
     * @param requestCode 请求码
     */
    public static void startActivityForResult(@NonNull final Fragment fragment,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode) {
        startActivityForResult(fragment, null, KUtilsGuide.getApp().getPackageName(), clz.getName(),
                requestCode, null);
    }

    /**
     * 启动Activity（带返回结果）
     *
     * @param fragment 当前的Fragment
     * @param clz 需要被打开的Activity类
     * @param requestCode 请求码
     * @param options 参数
     */
    public static void startActivityForResult(@NonNull final Fragment fragment,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              @Nullable final Bundle options) {
        startActivityForResult(fragment, null, KUtilsGuide.getApp().getPackageName(), clz.getName(),
                requestCode, options);
    }

    /**
     * 启动Activity（带返回结果）
     *
     * @param fragment 当前的Fragment
     * @param clz 需要被打开的Activity类
     * @param requestCode 请求码
     * @param sharedElements 要传输给被调用活动的共享元素的名称及其关联视图(<API:21 无效)
     */
    public static void startActivityForResult(@NonNull final Fragment fragment,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              final View... sharedElements) {
        startActivityForResult(fragment, null, KUtilsGuide.getApp().getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(fragment, sharedElements));
    }

    /**
     * 启动Activity（带返回结果）
     *
     * @param fragment 当前的Fragment
     * @param clz 需要被打开的Activity类
     * @param requestCode 请求码
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static void startActivityForResult(@NonNull final Fragment fragment,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              @AnimRes final int enterAnim,
                                              @AnimRes final int exitAnim) {
        startActivityForResult(fragment, null, KUtilsGuide.getApp().getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(fragment, enterAnim, exitAnim));
    }


    /**
     * 启动Activity（带返回结果）
     *
     * @param fragment 当前的Fragment
     * @param intent 要启动的活动的描述
     * @param requestCode 请求码
     */
    public static void startActivityForResult(@NonNull final Fragment fragment,
                                              @NonNull final Intent intent,
                                              final int requestCode) {
        startActivityForResult(intent, fragment, requestCode, null);
    }

    /**
     * 启动Activity（带返回结果）
     *
     * @param fragment 当前的Fragment
     * @param intent 要启动的活动的描述
     * @param requestCode 请求码
     * @param options 参数
     */
    public static void startActivityForResult(@NonNull final Fragment fragment,
                                              @NonNull final Intent intent,
                                              final int requestCode,
                                              @Nullable final Bundle options) {
        startActivityForResult(intent, fragment, requestCode, options);
    }

    /**
     * 启动Activity（带返回结果）
     *
     * @param fragment 当前的Fragment
     * @param intent 要启动的活动的描述
     * @param requestCode 请求码
     * @param sharedElements 要传输给被调用活动的共享元素的名称及其关联视图(<API:21 无效)
     */
    public static void startActivityForResult(@NonNull final Fragment fragment,
                                              @NonNull final Intent intent,
                                              final int requestCode,
                                              final View... sharedElements) {
        startActivityForResult(intent, fragment,
                requestCode, getOptionsBundle(fragment, sharedElements));
    }

    /**
     * 启动Activity（带返回结果）
     *
     * @param fragment 当前的Fragment
     * @param intent 要启动的活动的描述
     * @param requestCode 请求码
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static void startActivityForResult(@NonNull final Fragment fragment,
                                              @NonNull final Intent intent,
                                              final int requestCode,
                                              @AnimRes final int enterAnim,
                                              @AnimRes final int exitAnim) {
        startActivityForResult(intent, fragment,
                requestCode, getOptionsBundle(fragment, enterAnim, exitAnim));
    }

    /**
     * 启动多个Activity
     *
     * @param intents 要启动的活动的描述 数组
     */
    public static void startActivities(@NonNull final Intent[] intents) {
        startActivities(intents, getTopActivityOrApp(), null);
    }

    /**
     * 启动多个Activity
     *
     * @param intents 要启动的活动的描述 数组
     * @param options 参数
     */
    public static void startActivities(@NonNull final Intent[] intents,
                                       @Nullable final Bundle options) {
        startActivities(intents, getTopActivityOrApp(), options);
    }

    /**
     * 启动多个Activity
     *
     * @param intents 要启动的活动的描述 数组
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static void startActivities(@NonNull final Intent[] intents,
                                       @AnimRes final int enterAnim,
                                       @AnimRes final int exitAnim) {
        Context context = getTopActivityOrApp();
        startActivities(intents, context, getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 启动多个Activity
     *
     * @param activity 当前的Activity
     * @param intents 要启动的活动的描述 数组
     */
    public static void startActivities(@NonNull final Activity activity,
                                       @NonNull final Intent[] intents) {
        startActivities(intents, activity, null);
    }

    /**
     * 启动多个Activity
     *
     * @param activity 当前的Activity
     * @param intents 要启动的活动的描述 数组
     * @param options 参数
     */
    public static void startActivities(@NonNull final Activity activity,
                                       @NonNull final Intent[] intents,
                                       @Nullable final Bundle options) {
        startActivities(intents, activity, options);
    }

    /**
     * 启动多个Activity
     *
     * @param activity 当前的Activity
     * @param intents 要启动的活动的描述 数组
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static void startActivities(@NonNull final Activity activity,
                                       @NonNull final Intent[] intents,
                                       @AnimRes final int enterAnim,
                                       @AnimRes final int exitAnim) {
        startActivities(intents, activity, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 回到桌面
     */
    public static void startHomeActivity() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
    }

    /**
     * 打开启动页
     */
    public static void startLauncherActivity() {
        startLauncherActivity(KUtilsGuide.getApp().getPackageName());
    }

    /**
     * 打开启动页
     * @param pkg 包名
     */
    public static void startLauncherActivity(@NonNull final String pkg) {
        String launcherActivity = getLauncherActivity(pkg);
        if (TextUtils.isEmpty(launcherActivity)) return;
        startActivity(pkg, launcherActivity);
    }



    /**
     * 获取启动页名称
     *
     * @return 启动页名称
     */
    public static String getLauncherActivity() {
        return getLauncherActivity(KUtilsGuide.getApp().getPackageName());
    }

    /**
     * 获取启动页Activity名称
     *
     * @param pkg 包名
     * @return 启动页Activity名称
     */
    public static String getLauncherActivity(@NonNull final String pkg) {
        if (KUtilsGuide.isSpace(pkg)) return "";
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(pkg);
        PackageManager pm = KUtilsGuide.getApp().getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
        if (info == null || info.size() == 0) {
            return "";
        }
        return info.get(0).activityInfo.name;
    }

    /**
     * 获取入口Activity列表
     *
     * @return 入口Activity列表
     */
    public static List<String> getMainActivities() {
        return getMainActivities(KUtilsGuide.getApp().getPackageName());
    }

    /**
     * 获取入口Activity列表
     *
     * @param pkg 包名
     * @return 入口Activity列表
     */
    public static List<String> getMainActivities(@NonNull final String pkg) {
        List<String> ret = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.setPackage(pkg);
        PackageManager pm = KUtilsGuide.getApp().getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
        int size = info.size();
        if (size == 0) return ret;
        for (int i = 0; i < size; i++) {
            ResolveInfo ri = info.get(i);
            if (ri.activityInfo.processName.equals(pkg)) {
                ret.add(ri.activityInfo.name);
            }
        }
        return ret;
    }

    /**
     * 获取栈顶Activity
     *
     * @return 栈顶Activity
     */
    public static Activity getTopActivity() {
        return KUtilsGuide.getTopActivity();
    }

    /**
     * Activity是否存活
     *
     * @param context 上下文
     * @return true ==> 存在  false ==> 不存在
     */
    public static boolean isActivityAlive(final Context context) {
        return isActivityAlive(getActivityByContext(context));
    }

    /**
     * Activity是否存活
     *
     * @param activity Activity
     * @return true ==> 存在  false ==> 不存在
     */
    public static boolean isActivityAlive(final Activity activity) {
        return activity != null && !activity.isFinishing()
                && (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1 || !activity.isDestroyed());
    }

    /**
     * 该活动是否存在于活动的堆栈中
     *
     * @param activity Activity
     * @return true ==> 存在  false ==> 不存在
     */
    public static boolean isActivityExistsInStack(@NonNull final Activity activity) {
        List<Activity> activities = KUtilsGuide.getActivityList();
        for (Activity aActivity : activities) {
            if (aActivity.equals(activity)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 该活动是否存在于活动的堆栈中
     *
     * @param clz Activity类
     * @return true ==> 存在  false ==> 不存在
     */
    public static boolean isActivityExistsInStack(@NonNull final Class<? extends Activity> clz) {
        List<Activity> activities = KUtilsGuide.getActivityList();
        for (Activity aActivity : activities) {
            if (aActivity.getClass().equals(clz)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 结束Activity
     *
     * @param activity Activity本身
     */
    public static void finishActivity(@NonNull final Activity activity) {
        finishActivity(activity, false);
    }

    /**
     * 结束Activity
     *
     * @param activity Activity本身
     * @param isLoadAnim 是否使用退出动画
     */
    public static void finishActivity(@NonNull final Activity activity, final boolean isLoadAnim) {
        activity.finish();
        if (!isLoadAnim) {
            activity.overridePendingTransition(0, 0);
        }
    }

    /**
     * 结束Activity
     *
     * @param activity Activity本身
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static void finishActivity(@NonNull final Activity activity,
                                      @AnimRes final int enterAnim,
                                      @AnimRes final int exitAnim) {
        activity.finish();
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 结束Activity
     *
     * @param clz Activity类
     */
    public static void finishActivity(@NonNull final Class<? extends Activity> clz) {
        finishActivity(clz, false);
    }

    /**
     * 结束Activity
     *
     * @param clz Activity类
     * @param isLoadAnim 是否使用退出动画
     */
    public static void finishActivity(@NonNull final Class<? extends Activity> clz,
                                      final boolean isLoadAnim) {
        List<Activity> activities = KUtilsGuide.getActivityList();
        for (Activity activity : activities) {
            if (activity.getClass().equals(clz)) {
                activity.finish();
                if (!isLoadAnim) {
                    activity.overridePendingTransition(0, 0);
                }
            }
        }
    }

    /**
     * 结束Activity
     *
     * @param clz Activity类
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static void finishActivity(@NonNull final Class<? extends Activity> clz,
                                      @AnimRes final int enterAnim,
                                      @AnimRes final int exitAnim) {
        List<Activity> activities = KUtilsGuide.getActivityList();
        for (Activity activity : activities) {
            if (activity.getClass().equals(clz)) {
                activity.finish();
                activity.overridePendingTransition(enterAnim, exitAnim);
            }
        }
    }

    /**
     * 结束到指定 Activity
     *
     * @param activity      指定的Activity
     * @param isIncludeSelf 结束是否包含指定的Activity
     */
    public static boolean finishToActivity(@NonNull final Activity activity,
                                           final boolean isIncludeSelf) {
        return finishToActivity(activity, isIncludeSelf, false);
    }

    /**
     * 结束到指定 Activity
     *
     * @param activity      指定的Activity
     * @param isIncludeSelf 结束是否包含指定的Activity
     * @param isLoadAnim 是否使用退出动画
     */
    public static boolean finishToActivity(@NonNull final Activity activity,
                                           final boolean isIncludeSelf,
                                           final boolean isLoadAnim) {
        List<Activity> activities = KUtilsGuide.getActivityList();
        for (Activity act : activities) {
            if (act.equals(activity)) {
                if (isIncludeSelf) {
                    finishActivity(act, isLoadAnim);
                }
                return true;
            }
            finishActivity(act, isLoadAnim);
        }
        return false;
    }

    /**
     * 结束到指定 Activity
     *
     * @param activity      指定的Activity
     * @param isIncludeSelf 结束是否包含指定的Activity
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static boolean finishToActivity(@NonNull final Activity activity,
                                           final boolean isIncludeSelf,
                                           @AnimRes final int enterAnim,
                                           @AnimRes final int exitAnim) {
        List<Activity> activities = KUtilsGuide.getActivityList();
        for (Activity act : activities) {
            if (act.equals(activity)) {
                if (isIncludeSelf) {
                    finishActivity(act, enterAnim, exitAnim);
                }
                return true;
            }
            finishActivity(act, enterAnim, exitAnim);
        }
        return false;
    }

    /**
     * 结束到指定 Activity
     *
     * @param clz      Activity类
     * @param isIncludeSelf 结束是否包含指定的Activity
     */
    public static boolean finishToActivity(@NonNull final Class<? extends Activity> clz,
                                           final boolean isIncludeSelf) {
        return finishToActivity(clz, isIncludeSelf, false);
    }

    /**
     * 结束到指定 Activity
     *
     * @param clz      Activity类
     * @param isIncludeSelf 结束是否包含指定的Activity
     * @param isLoadAnim 是否使用退出动画
     */
    public static boolean finishToActivity(@NonNull final Class<? extends Activity> clz,
                                           final boolean isIncludeSelf,
                                           final boolean isLoadAnim) {
        List<Activity> activities = KUtilsGuide.getActivityList();
        for (Activity act : activities) {
            if (act.getClass().equals(clz)) {
                if (isIncludeSelf) {
                    finishActivity(act, isLoadAnim);
                }
                return true;
            }
            finishActivity(act, isLoadAnim);
        }
        return false;
    }

    /**
     * 结束到指定 Activity
     *
     * @param clz      Activity类
     * @param isIncludeSelf 结束是否包含指定的Activity
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static boolean finishToActivity(@NonNull final Class<? extends Activity> clz,
                                           final boolean isIncludeSelf,
                                           @AnimRes final int enterAnim,
                                           @AnimRes final int exitAnim) {
        List<Activity> activities = KUtilsGuide.getActivityList();
        for (Activity act : activities) {
            if (act.getClass().equals(clz)) {
                if (isIncludeSelf) {
                    finishActivity(act, enterAnim, exitAnim);
                }
                return true;
            }
            finishActivity(act, enterAnim, exitAnim);
        }
        return false;
    }

    /**
     * 结束所有其他类型的 Activity
     *
     * @param clz Activity类
     */
    public static void finishOtherActivities(@NonNull final Class<? extends Activity> clz) {
        finishOtherActivities(clz, false);
    }


    /**
     * 结束所有其他类型的 Activity
     *
     * @param clz Activity类
     * @param isLoadAnim 是否使用退出动画
     */
    public static void finishOtherActivities(@NonNull final Class<? extends Activity> clz,
                                             final boolean isLoadAnim) {
        List<Activity> activities = KUtilsGuide.getActivityList();
        for (Activity act : activities) {
            if (!act.getClass().equals(clz)) {
                finishActivity(act, isLoadAnim);
            }
        }
    }

    /**
     * 结束所有其他类型的 Activity
     *
     * @param clz Activity类
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static void finishOtherActivities(@NonNull final Class<? extends Activity> clz,
                                             @AnimRes final int enterAnim,
                                             @AnimRes final int exitAnim) {
        List<Activity> activities = KUtilsGuide.getActivityList();
        for (Activity act : activities) {
            if (!act.getClass().equals(clz)) {
                finishActivity(act, enterAnim, exitAnim);
            }
        }
    }

    /**
     * 结束所有的 Activity
     */
    public static void finishAllActivities() {
        finishAllActivities(false);
    }

    /**
     * 结束所有的 Activity
     *
     * @param isLoadAnim 是否使用退出动画
     */
    public static void finishAllActivities(final boolean isLoadAnim) {
        List<Activity> activityList = KUtilsGuide.getActivityList();
        for (Activity act : activityList) {
            // sActivityList remove the index activity at onActivityDestroyed
            act.finish();
            if (!isLoadAnim) {
                act.overridePendingTransition(0, 0);
            }
        }
    }

    /**
     * 结束所有的 Activity
     *
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static void finishAllActivities(@AnimRes final int enterAnim,
                                           @AnimRes final int exitAnim) {
        List<Activity> activityList = KUtilsGuide.getActivityList();
        for (Activity act : activityList) {
            // sActivityList remove the index activity at onActivityDestroyed
            act.finish();
            act.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 结束除最新之外的所有 Activity
     */
    public static void finishAllActivitiesExceptNewest() {
        finishAllActivitiesExceptNewest(false);
    }

    /**
     * 结束除最新之外的所有 Activity
     *
     * @param isLoadAnim 是否使用退出动画
     */
    public static void finishAllActivitiesExceptNewest(final boolean isLoadAnim) {
        List<Activity> activities = KUtilsGuide.getActivityList();
        for (int i = 1; i < activities.size(); i++) {
            finishActivity(activities.get(i), isLoadAnim);
        }
    }

    /**
     * 结束除最新之外的所有 Activity
     *
     * @param enterAnim 进入动画（被打开的Activity的动画）
     * @param exitAnim 离开动画（当前Activity的动画）
     */
    public static void finishAllActivitiesExceptNewest(@AnimRes final int enterAnim,
                                                       @AnimRes final int exitAnim) {
        List<Activity> activities = KUtilsGuide.getActivityList();
        for (int i = 1; i < activities.size(); i++) {
            finishActivity(activities.get(i), enterAnim, exitAnim);
        }
    }

    /**
     * 获取Activity Icon
     *
     * @param activity Activity
     * @return 返回 Activity Icon
     */
    @Nullable
    public static Drawable getActivityIcon(@NonNull final Activity activity) {
        return getActivityIcon(activity.getComponentName());
    }

    /**
     * 获取Activity Icon
     *
     * @param clz Activity类
     * @return 返回 Activity Icon
     */
    @Nullable
    public static Drawable getActivityIcon(@NonNull final Class<? extends Activity> clz) {
        return getActivityIcon(new ComponentName(KUtilsGuide.getApp(), clz));
    }

    /**
     * 获取Activity Icon
     *
     * @param activityName 组件名称
     * @return 返回 Activity Icon
     */
    @Nullable
    public static Drawable getActivityIcon(@NonNull final ComponentName activityName) {
        PackageManager pm = KUtilsGuide.getApp().getPackageManager();
        try {
            return pm.getActivityIcon(activityName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取Activity Logo
     *
     * @param activity Activity
     * @return 返回 Activity Logo
     */
    @Nullable
    public static Drawable getActivityLogo(@NonNull final Activity activity) {
        return getActivityLogo(activity.getComponentName());
    }

    /**
     * 获取Activity Logo
     *
     * @param clz Activity类
     * @return 返回 Activity Logo
     */
    @Nullable
    public static Drawable getActivityLogo(@NonNull final Class<? extends Activity> clz) {
        return getActivityLogo(new ComponentName(KUtilsGuide.getApp(), clz));
    }

    /**
     * 获取Activity Logo
     *
     * @param activityName 组件名称
     * @return 返回 Activity Logo
     */
    @Nullable
    public static Drawable getActivityLogo(@NonNull final ComponentName activityName) {
        PackageManager pm = KUtilsGuide.getApp().getPackageManager();
        try {
            return pm.getActivityLogo(activityName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void startActivity(final Context context,
                                      final Bundle extras,
                                      final String pkg,
                                      final String cls,
                                      @Nullable final Bundle options) {
        Intent intent = new Intent();
        if (extras != null) intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        startActivity(intent, context, options);
    }

    private static boolean startActivity(final Intent intent,
                                         final Context context,
                                         final Bundle options) {
        if (!isIntentAvailable(intent)) {
            Log.e("ActivityUtils", "intent is unavailable");
            return false;
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, options);
        } else {
            context.startActivity(intent);
        }
        return true;
    }

    private static boolean isIntentAvailable(final Intent intent) {
        return KUtilsGuide.getApp()
                .getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                .size() > 0;
    }

    private static boolean startActivityForResult(final Activity activity,
                                                  final Bundle extras,
                                                  final String pkg,
                                                  final String cls,
                                                  final int requestCode,
                                                  @Nullable final Bundle options) {
        Intent intent = new Intent();
        if (extras != null) intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        return startActivityForResult(intent, activity, requestCode, options);
    }

    private static boolean startActivityForResult(final Intent intent,
                                                  final Activity activity,
                                                  final int requestCode,
                                                  @Nullable final Bundle options) {
        if (!isIntentAvailable(intent)) {
            Log.e("ActivityUtils", "intent is unavailable");
            return false;
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.startActivityForResult(intent, requestCode, options);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
        return true;
    }

    private static void startActivities(final Intent[] intents,
                                        final Context context,
                                        @Nullable final Bundle options) {
        if (!(context instanceof Activity)) {
            for (Intent intent : intents) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivities(intents, options);
        } else {
            context.startActivities(intents);
        }
    }

    private static boolean startActivityForResult(final Fragment fragment,
                                                  final Bundle extras,
                                                  final String pkg,
                                                  final String cls,
                                                  final int requestCode,
                                                  @Nullable final Bundle options) {
        Intent intent = new Intent();
        if (extras != null) intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        return startActivityForResult(intent, fragment, requestCode, options);
    }

    private static boolean startActivityForResult(final Intent intent,
                                                  final Fragment fragment,
                                                  final int requestCode,
                                                  @Nullable final Bundle options) {
        if (!isIntentAvailable(intent)) {
            Log.e("ActivityUtils", "intent is unavailable");
            return false;
        }
        if (fragment.getActivity() == null) {
            Log.e("ActivityUtils", "Fragment " + fragment + " not attached to Activity");
            return false;
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            fragment.startActivityForResult(intent, requestCode, options);
        } else {
            fragment.startActivityForResult(intent, requestCode);
        }
        return true;
    }

    private static Bundle getOptionsBundle(final Fragment fragment,
                                           final int enterAnim,
                                           final int exitAnim) {
        Activity activity = fragment.getActivity();
        if (activity == null) return null;
        return ActivityOptionsCompat.makeCustomAnimation(activity, enterAnim, exitAnim).toBundle();
    }

    private static Bundle getOptionsBundle(final Context context,
                                           final int enterAnim,
                                           final int exitAnim) {
        return ActivityOptionsCompat.makeCustomAnimation(context, enterAnim, exitAnim).toBundle();
    }

    private static Bundle getOptionsBundle(final Fragment fragment,
                                           final View[] sharedElements) {
        Activity activity = fragment.getActivity();
        if (activity == null) return null;
        return getOptionsBundle(activity, sharedElements);
    }

    private static Bundle getOptionsBundle(final Activity activity,
                                           final View[] sharedElements) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return null;
        if (sharedElements == null) return null;
        int len = sharedElements.length;
        if (len <= 0) return null;
        @SuppressWarnings("unchecked")
        Pair<View, String>[] pairs = new Pair[len];
        for (int i = 0; i < len; i++) {
            pairs[i] = Pair.create(sharedElements[i], sharedElements[i].getTransitionName());
        }
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs).toBundle();
    }

    private static Context getTopActivityOrApp() {
        if (KUtilsGuide.isAppForeground()) {
            Activity topActivity = getTopActivity();
            return topActivity == null ? KUtilsGuide.getApp() : topActivity;
        } else {
            return KUtilsGuide.getApp();
        }
    }


    /**
     * 通过反射 获取App Activity列表
     * @return List<Activity>
     */
    public static List<Activity> getActivitiesByReflect(){
        LinkedList<Activity> list = new LinkedList<>();
        Activity topActivity = null;


        try {
            Object activityThread = getActivityThread();
            Field mActivityField = activityThread.getClass().getDeclaredField("mActivities");
            mActivityField.setAccessible(true);
            Object mActivities = mActivityField.get(activityThread);
            if (!(mActivities instanceof Map)){
                return list;
            }
            Map<Object,Object> binder_activityClientRecord_map = (Map<Object, Object>) mActivities;
            for (Object activityRecord : ((Map<Object, Object>) mActivities).values()) {
                Class activityClientRecordClass = activityRecord.getClass();
                Field acticityFidle = activityClientRecordClass.getDeclaredField("activity");
                acticityFidle.setAccessible(true);
                Activity activity = (Activity) acticityFidle.get(activityRecord);
                if(null == topActivity){
                    Field pauseField = activityClientRecordClass.getDeclaredField("paused");
                    pauseField.setAccessible(true);
                    if(!pauseField.getBoolean(activityRecord)){
                        topActivity = activity;
                    }else{
                        list.add(activity);
                    }
                }else{
                    list.add(activity);
                }
            }
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", "getActivitiesByReflect: " + e.getMessage());
        }

        if(null != topActivity){
            list.addFirst(topActivity);
        }
        return list;
    }


    /**
     * 获取应用ActivityThread
     * @return ActivityThread
     */
    public static Object getActivityThread(){
        Object activityThread = getActivityThreadInActivityThreadStaticField();
        return activityThread != activityThread ? activityThread : getActivityThreadInActivityThreadStaticMethod();
    }

    /**
     * 通过反射获取ActivityThread sCurrentActivityThread变量
     * @return currentActivityThread
     */
    public static Object getActivityThreadInActivityThreadStaticField(){
        try {
            Class activityThreadClass = Class.forName("androids.app.ActivityThread");
            Field curActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            curActivityThreadField.setAccessible(true);
            return curActivityThreadField.get(null);
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle","getActivityThreadInActivityThreadStaticField: " + e.getMessage());
            return null;
        }
    }

    /**
     * 通过反射获取ActivityThread currentActivityThread方法返回值
     * @return currentActivityThread
     */
    public static Object getActivityThreadInActivityThreadStaticMethod(){
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            return activityThreadClass.getMethod("currentActivityThread").invoke(null);
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInActivityThreadStaticMethod: " + e.getMessage());
            return null;
        }
    }

    /**
     * 通过反射获取应用Application
     * @return Application
     */
    public static Application getApplicationByReflect() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object thread = getActivityThread();
            Object app = activityThreadClass.getMethod("getApplication").invoke(thread);
            if (app == null) {
                return null;
            }
            return (Application) app;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
