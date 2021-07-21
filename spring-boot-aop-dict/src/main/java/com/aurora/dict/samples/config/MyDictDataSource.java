package com.aurora.dict.samples.config;
import com.aurora.dict.core.datasource.IDictDataSource;
import com.aurora.dict.core.entry.DictEntry;
import com.aurora.dict.core.entry.DictItemEntry;
import org.springframework.stereotype.Component;

/**
 * 模拟加载字典数据源
 * @author xzbcode
 */
@Component
public class MyDictDataSource implements IDictDataSource {

    @Override
    public DictEntry getDictSource() {
        return DictEntry.create(2)
                // 模拟添加用户性别字典
                .addDict("user_gender",
                        DictItemEntry.create(2)
                                .addItem("1", "男")
                                .addItem("2", "女")
                                .addItem("3", "保密")
                )
                // 模拟添加用户状态字典
                .addDict("user_status",
                        DictItemEntry.create(2)
                                .addItem("1", "有效")
                                .addItem("2", "无效")
                        );
    }

}
