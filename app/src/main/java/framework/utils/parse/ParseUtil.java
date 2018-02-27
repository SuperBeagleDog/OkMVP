package framework.utils.parse;


/**
 * @Author Lyf
 * @CreateTime 2018/2/26
 * @Description
 **/
public class ParseUtil {

    private static IParseUtil INSTANCE;

    private static IParseUtil getParseUtil() {

        if (INSTANCE == null) {
            synchronized (ParseUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GsonUtil();
                }
            }
        }

        return INSTANCE;
    }

    public static <T> T parseJson(String json, Class tClass) {
        return getParseUtil().parseJson(json, tClass);
    }

    public static String toJson(Object object) {
        return getParseUtil().toJson(object);
    }

}