package com.aurora.excel.core.handler;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.aurora.excel.core.styles.CellStyles;
import org.apache.poi.ss.usermodel.*;
import java.util.Iterator;

/**
 * 标记导入失败样式拦截器
 * @author xzbcode
 */
public class MarkFailedRowWriteHandler implements RowWriteHandler {

    private Integer importResultColumnIndex;
    private String successValue;

    public final static String IMPORT_SUCCESS = "导入成功";
    public final static String IMPORT_FAILURE = "导入失败";

    public MarkFailedRowWriteHandler(Integer importResultColumnIndex) {
        this.importResultColumnIndex = importResultColumnIndex;
        this.successValue = IMPORT_SUCCESS;
    }

    public MarkFailedRowWriteHandler(Integer importResultColumnIndex, String successValue) {
        this.importResultColumnIndex = importResultColumnIndex;
        this.successValue = successValue==null?IMPORT_SUCCESS:successValue;
    }

    @Override
    public void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer rowIndex, Boolean isHeader) {
        // 非表头的行
        if (!isHeader) {
            // 获取工作簿
            Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
            // 获取样式和字体
            CellStyle cellStyle = workbook.createCellStyle();
            Font cellFont = workbook.createFont();
            // 生成样式
            CellStyles.setFontColor(cellStyle, cellFont, IndexedColors.RED1);
            // 获取指定单元格
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getColumnIndex() == this.importResultColumnIndex) {
                    if (!this.successValue.equals(cell.getStringCellValue())) {
                        cell.setCellStyle(cellStyle);
                        break;
                    }
                }
            }
        }
    }

}
