package framework.utils.parse;

/**
 * @Author Lyf
 * @CreateTime 2018/2/26
 * @Description
 **/
public interface IParseUtil {

    /**
     * Converts a json string into a T bean.
     */
     <T> T parseJson(String json,  Class<T> tClass);

    /**
     * Converts a json string into a T bean. T should be a Interface generic type.
     */
     <T> T parseInterfaceGenericJson(String json,  Class tClass);

    /**
     * Converts an object into a string.
     */
    String toJson(Object object);
}
