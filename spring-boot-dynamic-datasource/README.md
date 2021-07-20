## 工程简介
SpringBoot中配置动态数据源，且与MyBatis结合使用

### 数据源存储
本案例中数据源为动态，所以存储在数据库中较为合适。

- 需要在数据库建立3个schema，分别为 `default_db`、`xiamen`、`quanzhou`

- 建表语句在 资源目录下的 `schemas` 文件夹中 

### 选择数据源
案例中数据源选择，是依靠请求中传过来的请求头 `TenantCode` 的值进行判断，正式项目中需要结合安全框架获取到当前用户的租户信息即可。
比如 将租户信息绑定到 JWT 令牌中。
~~~java
public class DynamicDataSourceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 模拟获取数据源标识，正式项目可以存放在JWT信息中
        String tenantCode = request.getHeader("TenantCode");
        // 设置当前线程的租户
        DataSourceHolder.set(tenantCode);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String tenantCode = request.getHeader("TenantCode");
        // 完成请求，清除租户信息
        DataSourceHolder.clear();
    }
}
~~~

### 测试
当前数据库有三个，default_db, quanzhou, xiamen
- 1.测试数据源是否成功切换
~~~text
 模拟请求区域

 GET: http://127.0.0.1:8090/area?id=1
 
Header参数： {TenantCode： xxxxxx}
    TenantCode可选值:
    - quanzhou ->  quanzhou库
    - xiamen   ->  xiamen库
    - default_db或其它  ->  default_db库
~~~

- 2.测试动态数据源事务是否生效
~~~text
模拟新增区域
 
POST: http://127.0.0.1:8090/area
 
Header参数： {TenantCode： xxxxxx}
    TenantCode可选值:
    - quanzhou ->  quanzhou库
    - xiamen   ->  xiamen库
    - default_db或其它  ->  default_db库

Body参数： id有值时候会抛出业务异常，新增业务会回滚
    {
    	"id": 10, 
    	"address": "厦门市思明区"
    }
~~~