package com.aurora.commons.tree;

public enum NodeType {

    ID("id"),
    LABEL("label"),
    PARENT_ID("parentId"),
    CHILDREN("children"),
    LEVEL("level");

    private String value;

    NodeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
