package com.aurora.excel.core.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.aurora.excel.core.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.groups.Default;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Excel解析监听器
 * @author xzbcode
 */
public abstract class ExcelAnalysisEventListener<T> extends AnalysisEventListener<T> {

    private Logger log = LoggerFactory.getLogger(ExcelAnalysisEventListener.class);

    /**导入批次号*/
    private final String importBatchNumber;
    /**导入对象名称*/
    private String modelName;
    /**导入结果集*/
    private List<T> resultList;
    /**总记录数*/
    private int totalCount;
    /**成功记录数*/
    private int succCount;
    /**失败记录数*/
    private int failCount;

    public ExcelAnalysisEventListener() {
        this.modelName = this.getClass().getSimpleName();
        this.importBatchNumber = UUID.randomUUID().toString().trim();
        this.resultList = new ArrayList<>();
    }

    public ExcelAnalysisEventListener(String modelName) {
        this.modelName = modelName;
        this.importBatchNumber = UUID.randomUUID().toString().trim();
        this.resultList = new ArrayList<>();
    }

    @Override
    public void invoke(T model, AnalysisContext analysisContext) {
        this.totalCount = this.totalCount + 1;
        try {
            // 解析处理
            this.validateModel(model);
            T successModel = this.analysisModel(model, analysisContext);
            if (successModel != null) {
                this.resultList.add(successModel);
            }
        } catch (Exception e) {
            this.failCount = this.failCount + 1;
            // 解析异常处理
            T failModel = this.handleException(model, e);
            if (failModel != null) {
                this.resultList.add(failModel);
            }
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        this.succCount = this.totalCount - this.failCount;
        String importModelName = "";
        if (this.modelName != null) {
            importModelName = this.modelName;
        } else {
            importModelName = analysisContext.getCustom().getClass().getName();
        }
        log.info(" -= (^-^) =- 导入{}完成，此次导入批次号为：{}，共计导入{}条，成功了{}条，失败了{}条",
                importModelName, this.importBatchNumber, this.totalCount, this.succCount, this.failCount);
        // 完成所有解析之后的处理
        this.doAfterAnalysed(analysisContext);
    }

    /**
     * <h2>校验每一行</h2>
     * @param model 该行的对象模型
     */
    public void validateModel(T model) throws ValidationException {
        if (this.isValidateModel()) {
            Class group = this.chooseValidateGroup();
            group = group == null ? Default.class : group;
            ValidatorUtil.validate(model, group);
        }
    }

    /**
     * <h2>是否开启默认的校验</h2>
     * @return
     */
    public boolean isValidateModel() {
        return false;
    }

    /**
     * <h2>选择校验的分组</h2>
     * 默认是 Default.class
     * @return
     */
    public Class chooseValidateGroup() {
        return Default.class;
    }

    /**
     * <h2>完成所有解析后的处理</h2>
     * @param analysisContext 解析上下文
     */
    public void doAfterAnalysed(AnalysisContext analysisContext) {}

    /**
     * <h2>解析每一行</h2>
     * @param model 该行的对象模型
     * @param analysisContext 解析上下文
     */
    public abstract T analysisModel(T model, AnalysisContext analysisContext);

    /**
     * <h2>抛出异常之后的处理</h2>
     * @param model 抛出异常所在行的对象模型
     * @param e 异常
     * @return
     */
    public abstract T handleException(T model, Exception e);


    public String getImportBatchNumber() {
        return importBatchNumber;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getSuccCount() {
        return succCount;
    }

    public int getFailCount() {
        return failCount;
    }

}
