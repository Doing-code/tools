package cn.forbearance.domain;

import org.apache.curator.framework.AuthInfo;

import java.util.List;

/**
 * @author cristina
 */
public class ZookeeperInfo {

    private String connectString;

    private String path;

    /**
     * 命名空间-隔离 暂不支持
     */
    private String namespace;

    /**
     * 操作权限 暂不支持
     */
    List<AuthInfo> authorization;

    public ZookeeperInfo() {
    }

    public ZookeeperInfo(String connectString, String path) {
        this.connectString = connectString;
        this.path = path;
    }

    public String getConnectString() {
        return connectString;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public List<AuthInfo> getAuthorization() {
        return authorization;
    }

    public void setAuthorization(List<AuthInfo> authorization) {
        this.authorization = authorization;
    }
}
