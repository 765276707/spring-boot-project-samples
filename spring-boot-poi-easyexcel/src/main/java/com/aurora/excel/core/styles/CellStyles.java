package com.aurora.excel.core.styles;

import org.apache.poi.ss.usermodel.*;

/**
 * 常用单元格样式
 * @author xzbcode
 */
public class CellStyles {

    /**
     * 设置单元格字体颜色
     * @param cellStyle 单元格样式
     * @param cellFont 单元格字体
     */
    public static void setFontColor(CellStyle cellStyle, Font cellFont, IndexedColors colors) {
        // 字体样式
        cellFont.setColor(colors.getIndex());
        cellFont.setFontName("宋体");
        cellFont.setFontHeightInPoints((short)11);
        cellStyle.setFont(cellFont);
    }

    /**
     * 设置单元格背景色
     * @param cellStyle 单元格样式
     * @param colors 背景色
     */
    public static void setBackgroundColor(CellStyle cellStyle, IndexedColors colors) {
        // 背景颜色
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillBackgroundColor(colors.getIndex());

    }

    /**
     * 设置单元格边框
     * @param cellStyle 单元格样式
     * @param borderStyle 边框样式
     */
    public static void setBorderStyle(CellStyle cellStyle, BorderStyle borderStyle) {
        cellStyle.setBorderTop(borderStyle);
        cellStyle.setBorderBottom(borderStyle);
        cellStyle.setBorderLeft(borderStyle);
        cellStyle.setBorderRight(borderStyle);
    }

}
