package io.renren.common.utils.ExcelUtil;

public class MainTest {

    public static void main(String[] args) {
        // 设定Excel文件所在路径
        String excelFileName = "C:\\Users\\Administrator\\Desktop\\2017-01.xlsx";
        // 读取Excel文件内容
        ExcelReader.readExcel(excelFileName,"2020-03");

        // todo 进行业务操作
    }

}
