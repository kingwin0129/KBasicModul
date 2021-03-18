package kingwin.utils.gather.constant;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 *     author: KingWin
 *     blog  : https://github.com/kingwin0129
 *     time  : 2021/03/23
 *     desc  : 内存常量
 * </pre>
 */
public final class MemoryConstants {

    public static final int BYTE = 1;
    public static final int KB   = 1024;
    public static final int MB   = 1048576;
    public static final int GB   = 1073741824;

    @IntDef({BYTE, KB, MB, GB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
