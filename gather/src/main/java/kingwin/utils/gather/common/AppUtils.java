package kingwin.utils.gather.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import kingwin.utils.gather.KUtilsGuide;

/**
 * @author KingWin
 * @since 2021/3/8 11:39 AM
 */

public class AppUtils {


    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * Install the app.
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param filePath The path of file.
     */
    public static void installApp(final String filePath) {
        installApp(KUtilsGuide.getFileByPath(filePath));
    }

    /**
     * Install the app.
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file The file.
     */
    public static void installApp(final File file) {
        Intent installAppIntent = KUtilsGuide.getInstallAppIntent(file);
        if (installAppIntent == null) return;
        KUtilsGuide.getApp().startActivity(installAppIntent);
    }

    /**
     * Install the app.
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param uri The uri.
     */
    public static void installApp(final Uri uri) {
        Intent installAppIntent = KUtilsGuide.getInstallAppIntent(uri);
        if (installAppIntent == null) return;
        KUtilsGuide.getApp().startActivity(installAppIntent);
    }

    /**
     * Uninstall the app.
     * <p>Target APIs greater than 25 must hold
     * Must hold {@code <uses-permission android:name="android.permission.REQUEST_DELETE_PACKAGES" />}</p>
     *
     * @param packageName The name of the package.
     */
    public static void uninstallApp(final String packageName) {
        if (KUtilsGuide.isSpace(packageName)) return;
        KUtilsGuide.getApp().startActivity(KUtilsGuide.getUninstallAppIntent(packageName));
    }

    /**
     * Return whether the app is installed.
     *
     * @param pkgName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppInstalled(final String pkgName) {
        if (KUtilsGuide.isSpace(pkgName)) return false;
        PackageManager pm = KUtilsGuide.getApp().getPackageManager();
        try {
            return pm.getApplicationInfo(pkgName, 0).enabled;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * Return whether the application with root permission.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppRoot() {
        ShellUtils.CommandResult result = KUtilsGuide.execCmd("echo root", true);
        return result.result == 0;
    }

    /**
     * Return whether it is a debug application.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppDebug() {
        return isAppDebug(KUtilsGuide.getApp().getPackageName());
    }

    /**
     * Return whether it is a debug application.
     *
     * @param packageName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppDebug(final String packageName) {
        if (KUtilsGuide.isSpace(packageName)) return false;
        try {
            PackageManager pm = KUtilsGuide.getApp().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Return whether it is a system application.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppSystem() {
        return isAppSystem(KUtilsGuide.getApp().getPackageName());
    }

    /**
     * Return whether it is a system application.
     *
     * @param packageName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppSystem(final String packageName) {
        if (KUtilsGuide.isSpace(packageName)) return false;
        try {
            PackageManager pm = KUtilsGuide.getApp().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Return whether application is foreground.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppForeground() {
        return KUtilsGuide.isAppForeground();
    }

    /**
     * Return whether application is foreground.
     * <p>Target APIs greater than 21 must hold
     * {@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" />}</p>
     *
     * @param pkgName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppForeground(@NonNull final String pkgName) {
        return !KUtilsGuide.isSpace(pkgName) && pkgName.equals(KUtilsGuide.getForegroundProcessName());
    }

    /**
     * Return whether application is running.
     *
     * @param pkgName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppRunning(final String pkgName) {
        if (KUtilsGuide.isSpace(pkgName)) return false;
        ActivityManager am = (ActivityManager) KUtilsGuide.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(Integer.MAX_VALUE);
            if (taskInfo != null && taskInfo.size() > 0) {
                for (ActivityManager.RunningTaskInfo aInfo : taskInfo) {
                    if (aInfo.baseActivity != null) {
                        if (pkgName.equals(aInfo.baseActivity.getPackageName())) {
                            return true;
                        }
                    }
                }
            }
            List<ActivityManager.RunningServiceInfo> serviceInfo = am.getRunningServices(Integer.MAX_VALUE);
            if (serviceInfo != null && serviceInfo.size() > 0) {
                for (ActivityManager.RunningServiceInfo aInfo : serviceInfo) {
                    if (pkgName.equals(aInfo.service.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Launch the application.
     *
     * @param packageName The name of the package.
     */
    public static void launchApp(final String packageName) {
        if (KUtilsGuide.isSpace(packageName)) return;
        Intent launchAppIntent = KUtilsGuide.getLaunchAppIntent(packageName);
        if (launchAppIntent == null) {
            Log.e("AppUtils", "Didn't exist launcher activity.");
            return;
        }
        KUtilsGuide.getApp().startActivity(launchAppIntent);
    }

    /**
     * Relaunch the application.
     */
    public static void relaunchApp() {
        relaunchApp(false);
    }

    /**
     * Relaunch the application.
     *
     * @param isKillProcess True to kill the process, false otherwise.
     */
    public static void relaunchApp(final boolean isKillProcess) {
        Intent intent = KUtilsGuide.getLaunchAppIntent(KUtilsGuide.getApp().getPackageName());
        if (intent == null) {
            Log.e("AppUtils", "Didn't exist launcher activity.");
            return;
        }
        intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
        );
        KUtilsGuide.getApp().startActivity(intent);
        if (!isKillProcess) return;
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * Launch the application's details settings.
     */
    public static void launchAppDetailsSettings() {
        launchAppDetailsSettings(KUtilsGuide.getApp().getPackageName());
    }

    /**
     * Launch the application's details settings.
     *
     * @param pkgName The name of the package.
     */
    public static void launchAppDetailsSettings(final String pkgName) {
        if (KUtilsGuide.isSpace(pkgName)) return;
        Intent intent = KUtilsGuide.getLaunchAppDetailsSettingsIntent(pkgName, true);
        if (!KUtilsGuide.isIntentAvailable(intent)) return;
        KUtilsGuide.getApp().startActivity(intent);
    }

    /**
     * Launch the application's details settings.
     *
     * @param activity    The activity.
     * @param requestCode The requestCode.
     */
    public static void launchAppDetailsSettings(final Activity activity, final int requestCode) {
        launchAppDetailsSettings(activity, requestCode, KUtilsGuide.getApp().getPackageName());
    }

    /**
     * Launch the application's details settings.
     *
     * @param activity    The activity.
     * @param requestCode The requestCode.
     * @param pkgName     The name of the package.
     */
    public static void launchAppDetailsSettings(final Activity activity, final int requestCode, final String pkgName) {
        if (activity == null || KUtilsGuide.isSpace(pkgName)) return;
        Intent intent = KUtilsGuide.getLaunchAppDetailsSettingsIntent(pkgName, false);
        if (!KUtilsGuide.isIntentAvailable(intent)) return;
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * Exit the application.
     */
    public static void exitApp() {
        KUtilsGuide.finishAllActivities();
        System.exit(0);
    }

    /**
     * Return the application's icon.
     *
     * @return the application's icon
     */
    @Nullable
    public static Drawable getAppIcon() {
        return getAppIcon(KUtilsGuide.getApp().getPackageName());
    }

    /**
     * Return the application's icon.
     *
     * @param packageName The name of the package.
     * @return the application's icon
     */
    @Nullable
    public static Drawable getAppIcon(final String packageName) {
        if (KUtilsGuide.isSpace(packageName)) return null;
        try {
            PackageManager pm = KUtilsGuide.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Return the application's icon resource identifier.
     *
     * @return the application's icon resource identifier
     */
    public static int getAppIconId() {
        return getAppIconId(KUtilsGuide.getApp().getPackageName());
    }

    /**
     * Return the application's icon resource identifier.
     *
     * @param packageName The name of the package.
     * @return the application's icon resource identifier
     */
    public static int getAppIconId(final String packageName) {
        if (KUtilsGuide.isSpace(packageName)) return 0;
        try {
            PackageManager pm = KUtilsGuide.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? 0 : pi.applicationInfo.icon;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Return the application's package name.
     *
     * @return the application's package name
     */
    @NonNull
    public static String getAppPackageName() {
        return KUtilsGuide.getApp().getPackageName();
    }

    /**
     * Return the application's name.
     *
     * @return the application's name
     */
    @NonNull
    public static String getAppName() {
        return getAppName(KUtilsGuide.getApp().getPackageName());
    }

    /**
     * Return the application's name.
     *
     * @param packageName The name of the package.
     * @return the application's name
     */
    @NonNull
    public static String getAppName(final String packageName) {
        if (KUtilsGuide.isSpace(packageName)) return "";
        try {
            PackageManager pm = KUtilsGuide.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? "" : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Return the application's path.
     *
     * @return the application's path
     */
    @NonNull
    public static String getAppPath() {
        return getAppPath(KUtilsGuide.getApp().getPackageName());
    }

    /**
     * Return the application's path.
     *
     * @param packageName The name of the package.
     * @return the application's path
     */
    @NonNull
    public static String getAppPath(final String packageName) {
        if (KUtilsGuide.isSpace(packageName)) return "";
        try {
            PackageManager pm = KUtilsGuide.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? "" : pi.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Return the application's version name.
     *
     * @return the application's version name
     */
    @NonNull
    public static String getAppVersionName() {
        return getAppVersionName(KUtilsGuide.getApp().getPackageName());
    }

    /**
     * Return the application's version name.
     *
     * @param packageName The name of the package.
     * @return the application's version name
     */
    @NonNull
    public static String getAppVersionName(final String packageName) {
        if (KUtilsGuide.isSpace(packageName)) return "";
        try {
            PackageManager pm = KUtilsGuide.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? "" : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Return the application's version code.
     *
     * @return the application's version code
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(KUtilsGuide.getApp().getPackageName());
    }

    /**
     * Return the application's version code.
     *
     * @param packageName The name of the package.
     * @return the application's version code
     */
    public static int getAppVersionCode(final String packageName) {
        if (KUtilsGuide.isSpace(packageName)) return -1;
        try {
            PackageManager pm = KUtilsGuide.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Return the application's signature.
     *
     * @return the application's signature
     */
    @Nullable
    public static Signature[] getAppSignatures() {
        return getAppSignatures(KUtilsGuide.getApp().getPackageName());
    }

    /**
     * Return the application's signature.
     *
     * @param packageName The name of the package.
     * @return the application's signature
     */
    @Nullable
    public static Signature[] getAppSignatures(final String packageName) {
        if (KUtilsGuide.isSpace(packageName)) return null;
        try {
            PackageManager pm = KUtilsGuide.getApp().getPackageManager();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES);
                if (pi == null) return null;

                SigningInfo signingInfo = pi.signingInfo;
                if (signingInfo.hasMultipleSigners()) {
                    return signingInfo.getApkContentsSigners();
                } else {
                    return signingInfo.getSigningCertificateHistory();
                }
            } else {
                PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
                if (pi == null) return null;

                return pi.signatures;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Return the application's signature.
     *
     * @param file The file.
     * @return the application's signature
     */
    @Nullable
    public static Signature[] getAppSignatures(final File file) {
        if (file == null) return null;
        PackageManager pm = KUtilsGuide.getApp().getPackageManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            PackageInfo pi = pm.getPackageArchiveInfo(file.getAbsolutePath(), PackageManager.GET_SIGNING_CERTIFICATES);
            if (pi == null) return null;

            SigningInfo signingInfo = pi.signingInfo;
            if (signingInfo.hasMultipleSigners()) {
                return signingInfo.getApkContentsSigners();
            } else {
                return signingInfo.getSigningCertificateHistory();
            }
        } else {
            PackageInfo pi = pm.getPackageArchiveInfo(file.getAbsolutePath(), PackageManager.GET_SIGNATURES);
            if (pi == null) return null;

            return pi.signatures;
        }
    }

    /**
     * Return the application's signature for SHA1 value.
     *
     * @return the application's signature for SHA1 value
     */
    @NonNull
    public static List<String> getAppSignaturesSHA1() {
        return getAppSignaturesSHA1(KUtilsGuide.getApp().getPackageName());
    }

    /**
     * Return the application's signature for SHA1 value.
     *
     * @param packageName The name of the package.
     * @return the application's signature for SHA1 value
     */
    @NonNull
    public static List<String> getAppSignaturesSHA1(final String packageName) {
        return getAppSignaturesHash(packageName, "SHA1");
    }

    /**
     * Return the application's signature for SHA256 value.
     *
     * @return the application's signature for SHA256 value
     */
    @NonNull
    public static List<String> getAppSignaturesSHA256() {
        return getAppSignaturesSHA256(KUtilsGuide.getApp().getPackageName());
    }

    /**
     * Return the application's signature for SHA256 value.
     *
     * @param packageName The name of the package.
     * @return the application's signature for SHA256 value
     */
    @NonNull
    public static List<String> getAppSignaturesSHA256(final String packageName) {
        return getAppSignaturesHash(packageName, "SHA256");
    }

    /**
     * Return the application's signature for MD5 value.
     *
     * @return the application's signature for MD5 value
     */
    @NonNull
    public static List<String> getAppSignaturesMD5() {
        return getAppSignaturesMD5(KUtilsGuide.getApp().getPackageName());
    }

    /**
     * Return the application's signature for MD5 value.
     *
     * @param packageName The name of the package.
     * @return the application's signature for MD5 value
     */
    @NonNull
    public static List<String> getAppSignaturesMD5(final String packageName) {
        return getAppSignaturesHash(packageName, "MD5");
    }

    /**
     * Return the application's user-ID.
     *
     * @return the application's signature for MD5 value
     */
    public static int getAppUid() {
        return getAppUid(KUtilsGuide.getApp().getPackageName());
    }

    /**
     * Return the application's user-ID.
     *
     * @param pkgName The name of the package.
     * @return the application's signature for MD5 value
     */
    public static int getAppUid(String pkgName) {
        try {
            return KUtilsGuide.getApp().getPackageManager().getApplicationInfo(pkgName, 0).uid;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static List<String> getAppSignaturesHash(final String packageName, final String algorithm) {
        ArrayList<String> result = new ArrayList<>();
        if (KUtilsGuide.isSpace(packageName)) return result;
        Signature[] signatures = getAppSignatures(packageName);
        if (signatures == null || signatures.length <= 0) return result;
        for (Signature signature : signatures) {
            String hash = KUtilsGuide.bytes2HexString(KUtilsGuide.hashTemplate(signature.toByteArray(), algorithm))
                    .replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
            result.add(hash);
        }
        return result;
    }

    /**
     * Return the application's information.
     * <ul>
     * <li>name of package</li>
     * <li>icon</li>
     * <li>name</li>
     * <li>path of package</li>
     * <li>version name</li>
     * <li>version code</li>
     * <li>is system</li>
     * </ul>
     *
     * @return the application's information
     */
    @Nullable
    public static AppInfo getAppInfo() {
        return getAppInfo(KUtilsGuide.getApp().getPackageName());
    }

    /**
     * Return the application's information.
     * <ul>
     * <li>name of package</li>
     * <li>icon</li>
     * <li>name</li>
     * <li>path of package</li>
     * <li>version name</li>
     * <li>version code</li>
     * <li>is system</li>
     * </ul>
     *
     * @param packageName The name of the package.
     * @return the application's information
     */
    @Nullable
    public static AppInfo getAppInfo(final String packageName) {
        try {
            PackageManager pm = KUtilsGuide.getApp().getPackageManager();
            if (pm == null) return null;
            return getBean(pm, pm.getPackageInfo(packageName, 0));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Return the applications' information.
     *
     * @return the applications' information
     */
    @NonNull
    public static List<AppInfo> getAppsInfo() {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = KUtilsGuide.getApp().getPackageManager();
        if (pm == null) return list;
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pi : installedPackages) {
            AppInfo ai = getBean(pm, pi);
            if (ai == null) continue;
            list.add(ai);
        }
        return list;
    }

    /**
     * Return the application's package information.
     *
     * @return the application's package information
     */
    @Nullable
    public static AppUtils.AppInfo getApkInfo(final File apkFile) {
        if (apkFile == null || !apkFile.isFile() || !apkFile.exists()) return null;
        return getApkInfo(apkFile.getAbsolutePath());
    }

    /**
     * Return the application's package information.
     *
     * @return the application's package information
     */
    @Nullable
    public static AppUtils.AppInfo getApkInfo(final String apkFilePath) {
        if (KUtilsGuide.isSpace(apkFilePath)) return null;
        PackageManager pm = KUtilsGuide.getApp().getPackageManager();
        if (pm == null) return null;
        PackageInfo pi = pm.getPackageArchiveInfo(apkFilePath, 0);
        if (pi == null) return null;
        ApplicationInfo appInfo = pi.applicationInfo;
        appInfo.sourceDir = apkFilePath;
        appInfo.publicSourceDir = apkFilePath;
        return getBean(pm, pi);
    }

    private static AppInfo getBean(final PackageManager pm, final PackageInfo pi) {
        if (pi == null) return null;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        String packageName = pi.packageName;
        ApplicationInfo ai = pi.applicationInfo;
        if (ai == null) {
            return new AppInfo(packageName, "", null, "", versionName, versionCode, false);
        }
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packagePath = ai.sourceDir;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        return new AppInfo(packageName, name, icon, packagePath, versionName, versionCode, isSystem);
    }

    /**
     * The application's information.
     */
    public static class AppInfo {

        private String   packageName;
        private String   name;
        private Drawable icon;
        private String   packagePath;
        private String   versionName;
        private int      versionCode;
        private boolean  isSystem;

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(final Drawable icon) {
            this.icon = icon;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public void setSystem(final boolean isSystem) {
            this.isSystem = isSystem;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(final String packageName) {
            this.packageName = packageName;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public void setPackagePath(final String packagePath) {
            this.packagePath = packagePath;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(final int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(final String versionName) {
            this.versionName = versionName;
        }

        public AppInfo(String packageName, String name, Drawable icon, String packagePath,
                       String versionName, int versionCode, boolean isSystem) {
            this.setName(name);
            this.setIcon(icon);
            this.setPackageName(packageName);
            this.setPackagePath(packagePath);
            this.setVersionName(versionName);
            this.setVersionCode(versionCode);
            this.setSystem(isSystem);
        }

        @Override
        @NonNull
        public String toString() {
            return "{" +
                    "\n    pkg name: " + getPackageName() +
                    "\n    app icon: " + getIcon() +
                    "\n    app name: " + getName() +
                    "\n    app path: " + getPackagePath() +
                    "\n    app v name: " + getVersionName() +
                    "\n    app v code: " + getVersionCode() +
                    "\n    is system: " + isSystem() +
                    "\n}";
        }
    }
}
