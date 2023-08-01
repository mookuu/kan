package util.wrapper;

import constant.Constants;

/**
 * @ClassName StringWrapper
 * @Description
 * @Author moku
 * @Date 2023/7/21 22:05
 * @Version 1.0
 */
public class StringWrapper {
    public static String valueOf(Object value) {
        if (value == null) {
            return null;
        }

        return value.toString();
    }

    public static String valueOfDefaultEmpty(Object value) {
        if (value == null) {
            return Constants.STRING_EMPTY;
        }

        return value.toString();
    }
}
