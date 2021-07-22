package com.aurora.commons.tree.samples;

import com.aurora.commons.tree.NodeType;
import com.aurora.commons.tree.TreeNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class Department {

    @TreeNode(type = NodeType.ID)
    private Long id;

    @TreeNode(type = NodeType.LABEL)
    private String deptName;

    @TreeNode(type = NodeType.PARENT_ID)
    private Long parentId;

    @TreeNode(type = NodeType.CHILDREN)
    private List<Department> children = new ArrayList<>();

    public Department(Long id, String deptName, Long parentId) {
        this.id = id;
        this.deptName = deptName;
        this.parentId = parentId;
    }

}
