package cn.forbearance.utils.enums;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cristina
 */
public enum DataType {

    /**
     * key typ
     */
    NONE("none"), STRING("string"), LIST("list"), SET("set"), ZSET("zset"), HASH("hash");

    private static final Map<String, DataType> codeLookup = new ConcurrentHashMap<>(7);

    static {
        for (DataType type : EnumSet.allOf(DataType.class))
            codeLookup.put(type.code, type);
    }

    private final String code;

    DataType(String name) {
        this.code = name;
    }

    public String code() {
        return code;
    }

    public static DataType fromCode(String code) {
        DataType data = codeLookup.get(code);
        if (data == null)
            throw new IllegalArgumentException("unknown data type code");
        return data;
    }
}
