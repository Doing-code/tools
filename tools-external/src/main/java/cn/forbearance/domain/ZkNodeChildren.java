package cn.forbearance.domain;

/**
 * @author cristina
 */
public class ZkNodeChildren {

    private String nodeId;

    private String nodeName;

    public ZkNodeChildren() {
    }

    public ZkNodeChildren(String nodeId, String nodeName) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
