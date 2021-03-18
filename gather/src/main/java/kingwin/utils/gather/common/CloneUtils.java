package kingwin.utils.gather.common;

import java.lang.reflect.Type;

import kingwin.utils.gather.KUtilsGuide;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/01/30
 *     desc  : utils about clone
 * </pre>
 */
public final class CloneUtils {

    private CloneUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Deep clone.
     *
     * @param data The data.
     * @param type The type.
     * @param <T>  The value type.
     * @return The object of cloned.
     */
    public static <T> T deepClone(final T data, final Type type) {
        try {
            return KUtilsGuide.fromJson(KUtilsGuide.toJson(data), type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
