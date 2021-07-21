package com.aurora.dict.core.datasource;

import com.aurora.dict.core.entry.DictEntry;

/**
 * 字典数据源获取接口
 * @author xzbcode
 */
public interface IDictDataSource {

    /**
     * 获取字典
     * @return DictEntry
     */
   DictEntry getDictSource();

}
