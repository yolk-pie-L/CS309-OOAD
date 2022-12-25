package com.example.live_video.util;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.export.styler.ExcelExportStylerDefaultImpl;
import org.apache.poi.ss.usermodel.*;

public class ExcelExportStatisticStyler extends ExcelExportStylerDefaultImpl {

    private CellStyle numberCellStyle;

    public ExcelExportStatisticStyler(Workbook workbook) {
        super(workbook);
        createNumberCellStyler();
    }

    private void createNumberCellStyler() {
        numberCellStyle = workbook.createCellStyle();
        numberCellStyle.setAlignment(HorizontalAlignment.CENTER);
        numberCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        DataFormat dataFormat = workbook.createDataFormat();
        //设置导出类型是是数值类型
        numberCellStyle.setDataFormat(dataFormat.getFormat("0.00_ "));
        numberCellStyle.setWrapText(true);
    }

    @Override
    public CellStyle getStyles(boolean noneStyler, ExcelExportEntity entity) {
        //自定义Dict转换
        if (entity != null
                && 10==entity.getType()) {
            return numberCellStyle;
        }
        return super.getStyles(noneStyler, entity);
    }
}