package com.aurora.commons.tree.samples;

import com.alibaba.fastjson.JSON;
import com.aurora.commons.tree.TreeUtil;

import java.util.ArrayList;
import java.util.List;

public class TreeTest {

    public static void main(String[] args) {
        // 模拟部门数据
        List<Department> departments = new ArrayList<>();
        departments.add(new Department(1L, "总部", 0L));
        departments.add(new Department(2L, "上海分公司", 1L));
        departments.add(new Department(3L, "厦门分公司", 1L));
        departments.add(new Department(4L, "上海分公司设计部", 2L));
        departments.add(new Department(5L, "厦门分公司研发部", 3L));
        // 常见树形列表
        List<Department> rows = TreeUtil.createTree(departments);
        // 打印树形列表
        System.out.println(JSON.toJSONString(rows));
    }
}
