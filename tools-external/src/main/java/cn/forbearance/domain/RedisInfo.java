package cn.forbearance.domain;

/**
 * @author cristina
 */
public class RedisInfo {

    private String key;

    private Object value;

    private String patternKey;

    private int position;

    private int count;

    private int keyType;


    /**
     * IP
     */
    private String ip;

    /**
     * 密码
     */
    private String password;

    /**
     * 端口
     */
    private Integer port;

    /**
     * ACL in Redis >= 6.0
     */
    private String account;

    public RedisInfo() {
    }

    public RedisInfo(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    public RedisInfo(String ip, String password, Integer port, String account) {
        this.ip = ip;
        this.password = password;
        this.port = port;
        this.account = account;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getPatternKey() {
        return patternKey;
    }

    public void setPatternKey(String patternKey) {
        this.patternKey = patternKey;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getKeyType() {
        return keyType;
    }

    public void setKeyType(int keyType) {
        this.keyType = keyType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAclUsername() {
        return account;
    }

    public void setAclUsername(String account) {
        this.account = account;
    }
}
