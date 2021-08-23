package com.example.SESummer.Entity;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataOutput {
    private static DataOutput instance = new DataOutput();
    private DataOutput(){};
    public static DataOutput getInstance(){
        return instance;
    }

    public String createExcel(List<Map<String,Object>> mapList,String filename,String title){
        Map<String,Object> map =mapList.get(0);
        Set<String> stringSet = map.keySet();
        ArrayList<String> headList = new ArrayList<>(stringSet);

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(title);
        sheet.setDefaultRowHeight((short) (1*256));
        for (int i = 0; i < headList.size(); i++) {
            sheet.setColumnWidth(i, 8000);
        }

        //设置单元格字体样式
        XSSFFont font = wb.createFont();
        font.setFontName("等线");
        font.setFontHeightInPoints((short) 16);

        XSSFRow titleRow = sheet.createRow(0);
        XSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);

        XSSFRow row = sheet.createRow(0);
        //根据数据源信息给每一列设置标题
        for (int i = 0; i < headList.size(); i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(headList.get(i));
        }

        XSSFRow rows;
        XSSFCell cells;
        //循环拿到的数据给所有行每一列设置对应的值
        for (int i = 0; i < mapList.size(); i++) {
            //在这个sheet页里创建一行
            rows = sheet.createRow(i + 1);
            //给该行数据赋值
            for (int j = 0; j < headList.size(); j++) {
                String value = mapList.get(i).get(headList.get(j)).toString();
                cells = rows.createCell(j);
                cells.setCellValue(value);
            }
        }

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        // 使用项目根目录, 文件名加上时间戳
        String path = "C:\\Users\\不觉\\OneDrive\\桌面\\软件工程小学期\\test" + "\\" + filename + dateFormat.format(date) + ".xlsx";
        try {
            File file = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            wb.write(fileOutputStream);
            wb.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;

    }
}
