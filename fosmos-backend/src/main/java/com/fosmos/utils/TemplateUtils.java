package com.fosmos.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.apache.xmlbeans.SchemaType;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTDataValidation;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.impl.CTDataValidationImpl;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TemplateUtils {

    private static String[] colIndex = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    public static XSSFWorkbook getTemplateExcel(String[] headers, Map<String,String[]> combData) {

        XSSFWorkbook wb = new XSSFWorkbook();

        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        XSSFFont fontStyle = wb.createFont();
        fontStyle.setFontName("微软雅黑");
        fontStyle.setFontHeightInPoints((short)12);
        fontStyle.setBold(true);
        style.setFont(fontStyle);

        XSSFSheet dataSheet = wb.createSheet("DataSheet");

        XSSFRow firstRow = dataSheet.createRow(0);
        for( int i = 0; i < headers.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            dataSheet.setColumnWidth(i,4000);
            cell.setCellStyle(style);
            cell.setCellValue(headers[i]);
        }

        XSSFSheet dictSheet = wb.createSheet("DictSheet");

        int i = 0;
        for(Map.Entry<String,String[]> entry : combData.entrySet()) {
            int maxRowNum = dictSheet.getLastRowNum();
            String[] values = entry.getValue();
            for( int j = 0; j < values.length; j++) {
                XSSFRow row;
                if( j >= maxRowNum ) {
                    row = dictSheet.createRow(j);
                }else{
                    row = dictSheet.getRow(j);
                }
                XSSFCell cell = row.createCell(i);
                cell.setCellValue(values[j]);
            }

            int col = -1;
            for( int j = 0; j < headers.length;j++) {
                if( headers[j].equals(entry.getKey())) {
                    col = j;
                    break;
                }
            }
            String strFormula = "DictSheet!$"+colIndex[i]+"$1:$"+colIndex[i]+"$"+ values.length;
            setValidationData( dataSheet,1, 10, col, col,strFormula);
            i++;
        }

        return wb;
    }



    /**
     * 添加数据有效性检查.
     * @param sheet 要添加此检查的Sheet
     * @param firstRow 开始行
     * @param lastRow 结束行
     * @param firstCol 开始列
     * @param lastCol 结束列
     * @param formular 有效性检查的下拉列表
     * @throws IllegalArgumentException 如果传入的行或者列小于0(< 0)或者结束行/列比开始行/列小
     */
    public static void setValidationData(Sheet sheet, int firstRow, int lastRow,
                                         int firstCol, int lastCol, String formular) throws IllegalArgumentException{
        if (firstRow < 0 || lastRow < 0 || firstCol < 0 || lastCol < 0 || lastRow < firstRow || lastCol < firstCol) {
            throw new IllegalArgumentException("Wrong Row or Column index : " + firstRow+":"+lastRow+":"+firstCol+":" +lastCol);
        }
        if (sheet instanceof XSSFSheet) {
            XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet)sheet);
            XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
                    .createFormulaListConstraint(formular);
                    //.createExplicitListConstraint(explicitListValues);
            CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
            XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
            validation.setSuppressDropDownArrow(true);
            validation.setShowErrorBox(true);
            sheet.addValidationData(validation);
        } else if(sheet instanceof HSSFSheet){
            CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
            DVConstraint dvConstraint = DVConstraint.createFormulaListConstraint(formular);
                    //DVConstraint.createExplicitListConstraint(set);
            DataValidation validation = new HSSFDataValidation(addressList, dvConstraint);
            validation.setSuppressDropDownArrow(true);
            validation.setShowErrorBox(true);
            sheet.addValidationData(validation);
        }
    }


    public static String getCellStringValue(Cell cell)
    {
        if( cell == null )
            return null;

        if (cell.getCellTypeEnum() == CellType.NUMERIC ) {
            return Double.valueOf(cell.getNumericCellValue()).toString();
        }
        if (cell.getCellTypeEnum() == CellType.STRING ) {
            return cell.getStringCellValue();
        }
        if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
            return Boolean.valueOf(cell.getBooleanCellValue()).toString();
        }
        if (cell.getCellTypeEnum() == CellType.FORMULA) {
            String val = cell.getStringCellValue();
            return val;
        }
        return null;
    }
}
