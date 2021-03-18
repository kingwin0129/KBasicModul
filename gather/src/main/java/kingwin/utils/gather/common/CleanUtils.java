package kingwin.utils.gather.common;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import java.io.File;

import kingwin.utils.gather.KUtilsGuide;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/27
 *     desc  : utils about clean
 * </pre>
 */
public final class CleanUtils {

    private CleanUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Clean the internal cache.
     * <p>directory: /data/data/package/cache</p>
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanInternalCache() {
        return KUtilsGuide.deleteAllInDir(KUtilsGuide.getApp().getCacheDir());
    }

    /**
     * Clean the internal files.
     * <p>directory: /data/data/package/files</p>
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanInternalFiles() {
        return KUtilsGuide.deleteAllInDir(KUtilsGuide.getApp().getFilesDir());
    }

    /**
     * Clean the internal databases.
     * <p>directory: /data/data/package/databases</p>
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanInternalDbs() {
        return KUtilsGuide.deleteAllInDir(new File(KUtilsGuide.getApp().getFilesDir().getParent(), "databases"));
    }

    /**
     * Clean the internal database by name.
     * <p>directory: /data/data/package/databases/dbName</p>
     *
     * @param dbName The name of database.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanInternalDbByName(final String dbName) {
        return KUtilsGuide.getApp().deleteDatabase(dbName);
    }

    /**
     * Clean the internal shared preferences.
     * <p>directory: /data/data/package/shared_prefs</p>
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanInternalSp() {
        return KUtilsGuide.deleteAllInDir(new File(KUtilsGuide.getApp().getFilesDir().getParent(), "shared_prefs"));
    }

    /**
     * Clean the external cache.
     * <p>directory: /storage/emulated/0/android/data/package/cache</p>
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanExternalCache() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && KUtilsGuide.deleteAllInDir(KUtilsGuide.getApp().getExternalCacheDir());
    }

    /**
     * Clean the custom directory.
     *
     * @param dirPath The path of directory.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanCustomDir(final String dirPath) {
        return KUtilsGuide.deleteAllInDir(KUtilsGuide.getFileByPath(dirPath));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void cleanAppUserData() {
        ActivityManager am = (ActivityManager) KUtilsGuide.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        //noinspection ConstantConditions
        am.clearApplicationUserData();
    }
}
