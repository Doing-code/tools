package cn.forbearance.domain;

/**
 * @author cristina
 */
public class RedisServer {

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
    private String aclUsername;

    public RedisServer() {
    }

    public RedisServer(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    public RedisServer(String ip, String password, Integer port, String aclUsername) {
        this.ip = ip;
        this.password = password;
        this.port = port;
        this.aclUsername = aclUsername;
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
        return aclUsername;
    }

    public void setAclUsername(String aclUsername) {
        this.aclUsername = aclUsername;
    }
}
