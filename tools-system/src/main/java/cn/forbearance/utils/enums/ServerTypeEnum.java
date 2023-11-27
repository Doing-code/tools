package cn.forbearance.utils.enums;

/**
 * @author cristina
 */
public enum ServerTypeEnum {

    /**
     * 服务器类型 1：Redis、2：MySQL、3：Zookeeper、4：Kafka
     */
    REDIS(1, "Redis"),
    MYSQL(2, "mysql"),
    ZOOKEEPER(3, "zookeeper"),
    KAFKA(4, "kafka");

    private int type;

    private String description;

    ServerTypeEnum(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
