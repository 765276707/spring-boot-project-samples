package com.aurora.dict.core.config;

import com.aurora.dict.core.aspect.DictAnalysisAspect;
import com.aurora.dict.core.datasource.IDictDataSource;
import com.aurora.dict.core.entry.DictEntry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * AOP字典自动装配
 * @author xzbcode
 */
public class DictSupportAutoConfiguration {

    @Bean
    public DictAnalysisAspect dictAnalysisAspect() {
        return new DictAnalysisAspect();
    }

    @Bean
    @ConditionalOnMissingBean(IDictDataSource.class)
    public IDictDataSource dictDataSource() {
        return new IDictDataSource() {
            @Override
            public DictEntry getDictSource() {
                return DictEntry.createEmpty();
            }
        };
    }
}
