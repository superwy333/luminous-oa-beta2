package cn.luminous.squab.model;

import cn.luminous.squab.entity.BaseDomain;

public class OaTaskNodeModel extends BaseDomain {

    private String nodeId;

    private String name;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
