## 工程简介
常用的工具类、辅助类、定制注解等集合

### domain
- page : 分页相关的模型
- response : 响应相关的模型，错误码定义

### stereotype

定制的注解，方便进行区分和查找，以及进行一些针对性的操作

- `@Interceptor` 标识拦截器
- `@KitTool`     标识工具组件
- `@Listener`    标识监听器
- `@Manager`     标识管理器
- `@OssClient`   标识OSS客户端
- `@TaskBean`    标识定时任务类

### tree
树形工具集合

- 标识属性
~~~java
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
~~~

- 生成树形列表
~~~java
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
~~~
- 查看结果
~~~json
[{
	"children": [{
		"children": [{
			"children": [],
			"deptName": "上海分公司设计部",
			"id": 4,
			"parentId": 2
		}],
		"deptName": "上海分公司",
		"id": 2,
		"parentId": 1
	}, {
		"children": [{
			"children": [],
			"deptName": "厦门分公司研发部",
			"id": 5,
			"parentId": 3
		}],
		"deptName": "厦门分公司",
		"id": 3,
		"parentId": 1
	}],
	"deptName": "总部",
	"id": 1,
	"parentId": 0
}]
~~~


### utils
常用工具包，包含加密、Web、反射、验证码等

