package com.aurora.commons.tree;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CascadeTreeUtil {


    private static final String item_id = "value";
    private static final String item_label = "label";
    private static final String item_parentId = "parentValue";
    private static final String item_children = "children";


    /**
     * 构建树形数据
     * @param nodes 数据列表
     * @return List
     */
    public static <T> List<Map<String, Object>> createTree(List<T> nodes) {
        List<Map<String, Object>> sources
                = nodes.stream()
                        .distinct()
                        .map(node -> {
                            Map<String, Object> item = new HashMap<>(4);
                            item.put(item_id, getValue(node, NodeType.ID));
                            item.put(item_label, getValue(node, NodeType.LABEL));
                            item.put(item_parentId, getValue(node, NodeType.PARENT_ID));
                            return item;
                        })
                        .collect(Collectors.toList());

        return sources.stream()
                .filter(item -> {
                    // 寻找子节点
                    findChildren(sources, item);
                    // 获取第一级节点
                    return !findParent(sources, item);
                })
                .collect(Collectors.toList());
    }

    /**
     * 寻找并添加子节点
     * @param nodes 部门类别
     */
    private static <T> void findChildren(List<Map<String, Object>> nodes, Map<String, Object> parent) {
        List<Map<String, Object>> children = nodes
                .stream()
                .filter(node -> {
                    return node.get(item_id)!=null
                                && node.get(item_parentId)!=null
                                && node.get(item_parentId).equals(parent.get(item_id));
                })
                .collect(Collectors.toList());
        if (children.size() > 0) {
            parent.put(item_children, children);
        }
    }


    /**
     * 查询列表中是否存在该节点的父节点
     * @param nodes 部门列表
     * @param child 子部门节点
     * @return boolean
     */
    private static <T> boolean findParent(List<Map<String, Object>> nodes, Map<String, Object> child) {
        return  nodes
                .stream()
                .anyMatch(node -> {
                    return node.get(item_id)!=null
                                    && node.get(item_id).equals(child.get(item_parentId));
                });
    }


    /**
     * 根据注解获取属性值
     * @param node
     * @param type
     * @param <T>
     * @return
     */
    private static <T> Object getValue(T node, NodeType type) {
        Field[] declaredFields = node.getClass().getDeclaredFields();
        Object result = null;
        for (Field field : declaredFields) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            TreeNode treeNode = field.getAnnotation(TreeNode.class);
            if (treeNode != null && treeNode.type()==type) {
                try {
                    result = field.get(node);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    break;
                }
                break;
            }
        }
        return result;
    }

}
