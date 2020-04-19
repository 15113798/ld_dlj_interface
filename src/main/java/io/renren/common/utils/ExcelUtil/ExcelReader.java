package io.renren.common.utils.ExcelUtil;

import io.renren.modules.generator.entity.DljIndustryDataEntity;
import io.renren.modules.generator.entity.DljIndustryEntity;
import io.renren.modules.generator.service.DljIndustryDataService;
import io.renren.modules.pc.common.CommonService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;



@Component
public class ExcelReader {

    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";




    /**
     * 根据文件后缀名类型获取对应的工作簿对象
     * @param inputStream 读取文件的输入流
     * @param fileType 文件后缀名类型（xls或xlsx）
     * @return 包含文件数据的工作簿对象
     */
    public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        if (fileType.equalsIgnoreCase(XLS)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase(XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }

    /**
     * 读取Excel文件内容
     * @param fileName 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败时返回null
     */
    public static List<DljIndustryDataEntity> readExcel(String fileName,String time) {

        Workbook workbook = null;
        FileInputStream inputStream = null;

        try {
            // 获取Excel后缀名
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            // 获取Excel文件
            File excelFile = new File(fileName);
            if (!excelFile.exists()) {
                //return null;
            }

            // 获取Excel工作簿
            inputStream = new FileInputStream(excelFile);
            workbook = getWorkbook(inputStream, fileType);

            // 读取excel中的数据
            List<DljIndustryDataEntity> list = parseExcel(workbook,time);
            return list;
        } catch (Exception e) {
            System.out.println("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
            //logger.warning("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
            //return null;
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                //logger.warning("关闭数据流出错！错误信息：" + e.getMessage());
                //return null;
            }
        }
        return null;
    }

    /**
     * 解析Excel数据
     * @param workbook Excel工作簿对象
     * @return 解析结果
     */
    private static List<DljIndustryDataEntity> parseExcel(Workbook workbook,String time) {
        //List<ExcelDataVO> resultDataList = new ArrayList<>();
        // 解析sheet
        List<DljIndustryDataEntity> resultDataList = new ArrayList<>();
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
            Sheet sheet = workbook.getSheetAt(sheetNum);

            // 校验sheet是否合法
            if (sheet == null) {
                continue;
            }

            // 获取第一行数据
            int firstRowNum = sheet.getFirstRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            if (null == firstRow) {
                //logger.warning("解析Excel失败，在第一行没有读取到任何数据！");
            }

            // 解析每一行的数据，构造数据对象
            int rowStart = firstRowNum + 1;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);

                if (null == row) {
                    continue;
                }

                DljIndustryDataEntity resultData = convertRowToData(row,time);
                /*if (null == resultData) {
                    logger.warning("第 " + row.getRowNum() + "行数据不合法，已忽略！");
                    continue;
                }*/
                resultDataList.add(resultData);
            }
        }

        return resultDataList;
    }


    /**
     * 提取每一行中需要的数据，构造成为一个结果数据对象
     *
     * 当该行中有单元格的数据为空或不合法时，忽略该行的数据
     *
     * @param row 行数据
     * @return 解析后的行数据对象，行数据错误时返回null
     */
    private static DljIndustryDataEntity convertRowToData(Row row,String time) {
        DljIndustryDataEntity resultData = new DljIndustryDataEntity();

        Cell cell;
        int cellNum = 0;
        // 获取行业
        cell = row.getCell(cellNum++);

        String name = cell.getStringCellValue();
        resultData.setIndustryName(name.trim());
        // 获取用户数
        cell = row.getCell(cellNum++);
        double userNum = cell.getNumericCellValue();
        String userNumStr = String.valueOf((new Double(userNum)).intValue());
        resultData.setUserNum(userNumStr);
        // 获取装机容量
        cell = row.getCell(cellNum++);
        double zjrl = cell.getNumericCellValue();
        String zjrlStr = String.valueOf((new Double(zjrl)).intValue());
        resultData.setInstalledCapacity(zjrlStr);
        // 获取用电量本月
        cell = row.getCell(cellNum++);
        double eleConMonth = cell.getNumericCellValue();
        String eleConMonthStr = String.valueOf((new Double(eleConMonth)));
        resultData.setEleConMonth(eleConMonthStr);
        // 用电量上年同月
        cell = row.getCell(cellNum++);
        double eleConOldYearMonth = cell.getNumericCellValue();
        String eleConOldYearMonthStr = String.valueOf((new Double(eleConOldYearMonth)));
        resultData.setEleConOldYearMonth(eleConOldYearMonthStr);
        // 本月同比
        cell = row.getCell(cellNum++);
        double compareMonth = cell.getNumericCellValue();
        String compareMonthStr = String.valueOf((new Double(compareMonth)));
        resultData.setCompareMonth(compareMonthStr);
        // 用电量本年
        cell = row.getCell(cellNum++);
        double eleConYear = cell.getNumericCellValue();
        String eleConYearStr = String.valueOf((new Double(eleConYear)));
        resultData.setEleConYear(eleConYearStr);
        // 用电量上年
        cell = row.getCell(cellNum++);
        double eleConOldYear = cell.getNumericCellValue();
        String eleConOldYearStr = String.valueOf((new Double(eleConOldYear)));
        resultData.setEleConOldYear(eleConOldYearStr);
        // 本年同比
        cell = row.getCell(cellNum++);
        double assYear = cell.getNumericCellValue();
        String assYearStr = String.valueOf((new Double(assYear)));
        resultData.setAssYear(assYearStr);

        resultData.setIndustryCapUtil(calIndCapUtil(eleConMonthStr,zjrlStr,2));
        resultData.setRecordTime(time);
        resultData.setChainRatio("10%");

        return resultData;
    }



    /*
        计算本月行业产能利用率
        入参：用电量，装机量。
     */
    public static String calIndCapUtil(String yd, String zj, int yearOrMonth){
        int timeCount = 0;
        if(yearOrMonth == 1){
            timeCount = 24*30*365;
        }else{
            timeCount = 24*30;
        }

        if(!zj.equals("0")){
            BigDecimal ydBig = new BigDecimal(yd);
            BigDecimal zjBig = new BigDecimal(zj);

            BigDecimal indBig = ydBig.multiply(new BigDecimal(10000)).divide(zjBig, 4, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(timeCount), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
            DecimalFormat df1 = new DecimalFormat("0.00");
            String str = df1.format(indBig);
            return str + "%";
        }

        return "0.00%";
    }


}
