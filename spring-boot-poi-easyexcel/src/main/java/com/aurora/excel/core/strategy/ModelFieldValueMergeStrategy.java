package com.aurora.excel.core.strategy;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.*;

/**
 * 根据某个字段的值去合并某几列
 * @author xzbcode
 */
public class ModelFieldValueMergeStrategy extends AbstractMergeStrategy {

    // 判断的列，要求此列不为空
    private final String judgefieldName;
    // 合并的列
    private final Set<Integer> needMergeColumnIndexs = new HashSet<>();

    // 存储已合并的值
    private final Map<String, Object> recordMap = new HashMap<>();
    private int startRow = 1;

    public ModelFieldValueMergeStrategy(String judgefieldName, int ...needMergeColumnIndex) {
        this.judgefieldName = judgefieldName;
        Arrays.stream(needMergeColumnIndex).forEach(item -> {
            this.needMergeColumnIndexs.add(item);
        });
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
        /**
         * startRow  range
         * 1    1-2
         * 3    3-5
         * 6    6-6
         * 7    7-7
         */
        String fieldName = head.getFieldName();
        if (judgefieldName.equals(fieldName)) {
            int columnIndex = cell.getColumnIndex();
            int rowIndex = cell.getRowIndex();
            String key = this.generatorKey(rowIndex, columnIndex);
            Object cellValue = this.getCellStringValue(cell);
            if (!recordMap.containsKey(key)) {
                recordMap.put(key, cellValue);
            }
            if (rowIndex > 1) {
                // 获取上一行的值
                String lastKey = this.generatorLastKey(rowIndex, columnIndex);
                if (recordMap.containsKey(lastKey)) {
                    Object lastRowCellValue = recordMap.get(lastKey);
                    // 跟上一行同一列值是否一致
                    if (!"".equals(cellValue) && !lastRowCellValue.equals(cellValue)) {
                        boolean needMerge = (rowIndex-this.startRow) > 1;
                        if (needMerge) {
                            try {
                                this.mergedRegionUnsafe(sheet, rowIndex-1, needMergeColumnIndexs);
                            } finally {
                                this.startRow = rowIndex;
                            }
                        } else {
                            this.startRow = rowIndex;
                        }
                    }
                }
            }
        }
    }

    private void mergedRegionUnsafe(Sheet sheet, int currentRow, Set<Integer> needMergeColumnIndexs) {
        for (Integer columnIndex : needMergeColumnIndexs) {
            sheet.addMergedRegionUnsafe(new CellRangeAddress(this.startRow, currentRow, columnIndex, columnIndex));
        }
    }

    private Object getCellStringValue(Cell cell) {
        CellType cellTypeEnum = cell.getCellTypeEnum();
        Object cellVaue = null;
        switch (cellTypeEnum) {
            case STRING:
                cellVaue = cell.getStringCellValue();
                break;
            case BOOLEAN:
                cellVaue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                cellVaue = cell.getCellFormula();
                break;
            case NUMERIC:
                cellVaue = cell.getNumericCellValue();
                break;
            case ERROR:
                cellVaue = cell.getErrorCellValue();
                break;
            default:
                cellVaue = "";
        }
        return cellVaue;
    }

    private String generatorKey(int rowIndex, int colIndex) {
        return rowIndex + ":" + colIndex;
    }

    private String generatorLastKey(int rowIndex, int colIndex) {
        rowIndex = rowIndex - 1;
        return this.generatorKey(rowIndex, colIndex);
    }

}
