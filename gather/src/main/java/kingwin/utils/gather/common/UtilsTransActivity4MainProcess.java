package kingwin.utils.gather.common;

import android.app.Activity;
import android.content.Intent;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2020/03/19
 *     desc  :
 * </pre>
 */
public class UtilsTransActivity4MainProcess extends com.blankj.utilcode.util.UtilsTransActivity {

    public static void start(final TransActivityDelegate delegate) {
        start(null, null, delegate, com.blankj.utilcode.util.UtilsTransActivity4MainProcess.class);
    }

    public static void start(final Utils.Consumer<Intent> consumer,
                             final TransActivityDelegate delegate) {
        start(null, consumer, delegate, com.blankj.utilcode.util.UtilsTransActivity4MainProcess.class);
    }

    public static void start(final Activity activity,
                             final TransActivityDelegate delegate) {
        start(activity, null, delegate, com.blankj.utilcode.util.UtilsTransActivity4MainProcess.class);
    }

    public static void start(final Activity activity,
                             final Utils.Consumer<Intent> consumer,
                             final TransActivityDelegate delegate) {
        start(activity, consumer, delegate, com.blankj.utilcode.util.UtilsTransActivity4MainProcess.class);
    }
}
