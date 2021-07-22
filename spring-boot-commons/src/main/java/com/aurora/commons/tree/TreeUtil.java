package com.aurora.commons.tree;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h1>树解析器</h1>
 * @author xzb
 */
public class TreeUtil {

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

    /**
     * 设置值
     * @param node
     * @param value
     * @param <T>
     */
    private static <T> void setChildren(T node, Object value) {
        Field[] declaredFields = node.getClass().getDeclaredFields();
        Object result = null;
        for (Field field : declaredFields) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            TreeNode treeNode = field.getAnnotation(TreeNode.class);
            if (treeNode != null && treeNode.type()==NodeType.CHILDREN) {
                try {
                    field.set(node, value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    break;
                }
                break;
            }
        }
    }


    /**
     * 构建树形数据
     * @param nodes 数据列表
     * @return List
     */
    public static <T> List<T> createTree(List<T> nodes) {
        return nodes.stream()
                .filter(item -> {
                    // 寻找子节点
                    findChildren(nodes, item);
                    // 获取第一级节点
                    return !findParent(nodes, item);
                })
                .collect(Collectors.toList());
    }

    /**
     * 寻找并添加子节点
     * @param nodes 部门类别
     */
    private static <T> void findChildren(List<T> nodes, T parent) {
        List<T> children = nodes
                .stream()
                .filter(node -> {
                    return getValue(node, NodeType.ID)!=null
                            && getValue(node, NodeType.PARENT_ID).equals(getValue(parent, NodeType.ID));
                })
                .collect(Collectors.toList());
        setChildren(parent, children);
    }


    /**
     * 查询列表中是否存在该节点的父节点
     * @param nodes 部门列表
     * @param child 子部门节点
     * @return boolean
     */
    private static <T> boolean findParent(List<T> nodes, T child) {
        return  nodes
                .stream()
                .anyMatch(node -> {
                    return getValue(node, NodeType.ID).equals(getValue(child, NodeType.PARENT_ID));
                });
    }

    /**
     * 计算要展开的节点编号集合
     * @param list 节点列表
     * @param expandLevel 展开的层级
     * @param <T>
     * @return Set
     */
    public static <T> Set<String> calcExpandRowKeys(List<T> list, int expandLevel) {
        return list.stream()
                .filter(node -> {
                    Object level = getValue(node, NodeType.LEVEL);
                    return level!=null && (int)level <= expandLevel;
                })
                .map(node -> String.valueOf(getValue(node, NodeType.ID)))
                .collect(Collectors.toSet());
    }
}
