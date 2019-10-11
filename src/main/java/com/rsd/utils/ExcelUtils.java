package com.rsd.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    /**
     * @param header   要到处的Excel列头
     * @param dataList 数据集合，元素类型为字符串组数，数组的长度必须和列头相等
     * @return 返回excel对象，输出到页面
     */
    public static HSSFWorkbook importExcel(String[] header, List<String[]> dataList) {

        // 校验数据是否合法
        if (header == null || header.length == 0)
            return null;

        if (dataList == null || dataList.size() == 0)
            return null;

        if (dataList.get(0).length != header.length)
            return null;

        // 创建一个ExceL
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建一个sheet
        HSSFSheet sheet = wb.createSheet("产品列表（" + DateUtil.getNowDateString() + "）");

        // 1、-----处理列头，对齐方式为居中----
        HSSFRow row = sheet.createRow((int) 0);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        for (int i = 0; i < header.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(header[i]);
            cell.setCellStyle(style);
            sheet.autoSizeColumn((short) i);
        }
        // ------列头处理完毕------

        // 2、-------处理数据-------
        String rowdata[] = null;
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow(i + 1);
            rowdata = dataList.get(i);
            for (int c = 0; c < rowdata.length; c++) {
                row.createCell(c).setCellValue(rowdata[c]);
            }
        }
        // 2、-------处理数据完毕-------
        return wb;
    }

    @SuppressWarnings("rawtypes")
    public static HSSFWorkbook getDeviceLoadHssfWorkbook(String sheetName, String[] titles, String[] columns, List list) {
        // 新建工作薄对象
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建一张表sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        // //设置C,H,I列的宽度，按表头单元格宽度
        // sheet.setColumnWidth(2, 14*256);
        // sheet.setColumnWidth(7, 12*256);
        // sheet.setColumnWidth(8, 16*256);
        // 第0行，即表头
        HSSFRow header = sheet.createRow(0);
        // 生成表头
        header.createCell(0).setCellValue("序号");
        for (int i = 0; i < titles.length; i++) {
            header.createCell(i + 1).setCellValue(titles[i]);
        }
        // 填充数据
        for (long i = 0; i < list.size(); i++) {
            HSSFRow row = sheet.createRow((int) (i + 1));
            row.createCell(0).setCellValue(i + 1);// ----序号列
            Map map = (Map) list.get((int) i);
            for (int j = 0; j < titles.length; j++) {
                row.createCell((int) (j + 1)).setCellValue((String) map.get(columns[j]));
            }
        }
        return wb;
    }


    /***
     * 这是一个通用的方法，利用了JAVA的反射机制，将读取excel文件内容映射到List
     * excel-object.properties文件配置exel文件与对象的对照关系 ProjectDir =
     * [{},{"name":"id","text"
     * :"XXX"},{"name":"code","text":"编码","len":"20","format":"#0"}] {},表示跳过第一列
     *
     * @param excelFile
     *            excel文件
     * @param c
     *            返回对象类型
     * @return list 对象列表
     * @throws Exception
     */
    public static <T> List<T> readXls(String excelFile, Class<T> c) throws Exception {
        ConfigurationManager cm = ConfigurationManager.getManager("excel-object");
        List<Map<String, String>> properties = null;
        String tmp = "";
        tmp = cm.getProperty(c.getSimpleName());// ---依据配置文件读取excel文件中的各列与Class的属性的对照关系
        if (tmp == null || "".equals(tmp)) {
            throw new Exception("未配置excel文件信息");
        } else {
            // ----tmp数据
            ObjectMapper om = new ObjectMapper();
            properties = om.readValue(tmp, new TypeReference<List<Map<String, String>>>() {
            });
        }
        // ----读excel文件
        InputStream is = new FileInputStream(excelFile);
        Workbook hssfWorkbook = null;

        if (excelFile.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);
        } else if (excelFile.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);
        }
        T obj = null;
        List<T> list = new ArrayList<T>();
        // 循环工作表Sheet 只读第一个工作表
        //for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++){//这是读取所有工作表
        for (int numSheet = 0; numSheet < 1; numSheet++) {
            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            Row firstHssfRow = hssfSheet.getRow(0);
            if (firstHssfRow == null) {
                throw new Exception("Excel文件没有内容");
            } else if (firstHssfRow.getLastCellNum() != properties.size()) {
                throw new Exception("Excel文件不匹配：要求列数" + properties.size() + "，文件中的列数" + firstHssfRow.getLastCellNum());
            }
            // 循环行Row（从最后一行开始，到1行，不包括标题行）
            for (int rowNum = hssfSheet.getLastRowNum(); rowNum >= 1; rowNum--) {
                Row hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) {
                    continue;
                }
                obj = c.newInstance();
                // 循环列Cell
                short maxIndex = hssfRow.getLastCellNum();
                for (int i = 0; i < properties.size(); i++) {
                    Map<String, String> oneCol = properties.get(i);
                    if (oneCol == null) {
                        continue;
                    } else {
                        String name = oneCol.get("name");
                        String text = oneCol.get("text");
                        String format = oneCol.get("format");
                        Object fieldValue = null;
                        String type = "";
                        if (i >= maxIndex) {
                            continue;
                            // throw new Exception(text +
                            // ",Excel文件不存在第"+(i+1)+"列");
                        } else {
                            Cell cell = hssfRow.getCell(i);
                            if (cell == null) {// ---没有内容的
                                continue;// ----没有数据的，跳过
                            }
                            if (name == null) {// ---没有设置属性名称的，跳过
                                continue;
                            }
                            try {
                                type = c.getDeclaredField(name).getGenericType().toString();
                            } catch (Exception e) {
                                throw new Exception("设置的读取属性:" + name + " 错误");
                            }
                            if (text == null || "".equals(text)) {
                                text = name;
                            }

                            fieldValue = getValue(cell, type, format);// ----值转换
                        }
                        Method m; // -----执行setXXXX方法为对象赋值
                        // ----方法名，在属性名前加上set
                        String setName = "set" + name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
                        if (type.equals("class java.lang.Integer")) {
                            // ----整型
                            m = c.getMethod(setName, Integer.class);
                        } else if (type.equals("class java.util.Date")) {
                            m = c.getMethod(setName, Date.class);
                        } else {
                            // ----其它的，按字符处理
                            tmp = oneCol.get("len");
                            m = c.getMethod(setName, String.class);
                            // ----长度判断
                            if (fieldValue != null) {
                                if (!(tmp == null || "".equals(tmp))) {
                                    int len = Integer.valueOf(tmp);
                                    int valLen = ((String) fieldValue).getBytes().length;
                                    if (valLen > len) {
                                        throw new Exception("第" + (rowNum + 1) + "行，" + text + "内容长度(" + valLen + ")大于" + len + "，不能保存");
                                    }
                                }
                            }
                        }
                        try {
                            // -----执行setXXXX方法为对象赋值
                            m.invoke(obj, fieldValue);
                        } catch (Exception e) {
                            throw new Exception(e.getMessage() + "(" + "第" + (rowNum + 1) + "行，" + text + "内容：" + fieldValue + " 属性赋值失败" + ")");
                        }
                    }
                }
                if (obj != null) {
                    list.add(obj);
                }
                hssfSheet.removeRow(hssfRow);
            }
        }
        return list;
    }


    public static List<List<String>> readExcel(String excelFile) throws Exception {
        List<List<String>> list = new ArrayList<List<String>>();

        // ----读excel文件
        InputStream is = new FileInputStream(excelFile);
        Workbook hssfWorkbook = null;

        if (excelFile.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);
        } else if (excelFile.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);
        }

        Sheet hssfSheet = hssfWorkbook.getSheetAt(0);

        Row firstHssfRow = hssfSheet.getRow(0);

        if (firstHssfRow == null) {
            return null;
        }

        int rowNum = hssfSheet.getLastRowNum();
        int cellNum = firstHssfRow.getLastCellNum();

        for (int i = 0; i <= rowNum; i++) {
            Row hssfRow = hssfSheet.getRow(i);
            List<String> rlist = new ArrayList<String>();
            if (hssfRow != null) {
                for (int j = 0; j < cellNum; j++) {
                    if (hssfRow.getCell(j) == null) {
                        rlist.add("");
                    } else {
                        String tmp = getValue(hssfRow.getCell(j), "class java.lang.String", "").toString();
                        rlist.add(tmp);
                    }
                }
                list.add(rlist);
            }
        }

        return list;
    }


    /**
     * 得到Excel表中的值，并根据dataType转换
     *
     * @param hssfCell Excel中的每一个格子
     * @return Excel中每一个格子中的值
     */
    private static Object getValue(Cell hssfCell, String dataType, String format) {
        Object rtn = null;
        if (hssfCell == null) {
            return null;
        } else if (dataType.equals("class java.lang.Integer")) {
            rtn = Double.valueOf(hssfCell.getNumericCellValue()).intValue();
        } else if (dataType.equals("class java.lang.Double")) {
            rtn = hssfCell.getNumericCellValue();
        } else if (dataType.equals("class java.util.Date")) {
            rtn = hssfCell.getDateCellValue();
        } else if (dataType.equals("class java.lang.String")) {
            if (hssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                // 返回数值类型的值
                short dataFormatType = hssfCell.getCellStyle().getDataFormat();
                if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {// ---日期数据
                    Date d = hssfCell.getDateCellValue();
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                    rtn = sf.format(d);
                } else if (dataFormatType == 31 || dataFormatType == 32 || dataFormatType == 58) {
                    Date d = hssfCell.getDateCellValue();
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                    rtn = sf.format(d);
                } else if (format != null && !format.equals("")) {// ---按指定格式输出
                    rtn = new DecimalFormat(format).format(hssfCell.getNumericCellValue());
                } else {// ---无格式要求
                    rtn = new DecimalFormat("0.###############").format(hssfCell.getNumericCellValue());
                }
            } else {
                switch (hssfCell.getCellType()) {
                    case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                        hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        rtn = hssfCell.getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_STRING: // 字符串
                        hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        rtn = hssfCell.getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                        hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        rtn = hssfCell.getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA: // 公式
                        hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        rtn = hssfCell.getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_BLANK: // 空值
                        rtn = "";
                        break;
                    case HSSFCell.CELL_TYPE_ERROR: // 故障
                        rtn = "";
                        break;
                    default:
                        rtn = "";
                        break;
                }
            }
        }
        return rtn;
    }

    /**
     * 导入等级医院
     *
     * @param excelFile
     * @return
     * @throws Exception
     */
    public static List<List<String>> importExcel(String excelFile) throws Exception {
        List<List<String>> list = new ArrayList<List<String>>();
        // ----读excel文件
        InputStream is = new FileInputStream(excelFile);
        Workbook hssfWorkbook = null;

        if (excelFile.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);
        } else if (excelFile.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);
        }

        Sheet hssfSheet = hssfWorkbook.getSheetAt(0);

        Row firstHssfRow = hssfSheet.getRow(0);

        if (firstHssfRow == null) {
            return null;
        }

        //总行数
        int rowNum = hssfSheet.getLastRowNum();
        //读取列数
        int cellNum = 7;
        int _rowNum = 0;

        for (int i = 1; i <= rowNum; i++) {
            Row row = hssfSheet.getRow(i);
            if (null != row && !getValue(row.getCell(0), "class java.lang.String", "").toString().equals("")) {
                _rowNum = i;
            } else {
                break;
            }
        }

        if (_rowNum < 1) {
            return null;
        }

        for (int i = 1; i <= _rowNum; i++) {
            Row hssfRow = hssfSheet.getRow(i);
            List<String> rlist = new ArrayList<String>();
            if (hssfRow != null) {

                if (null == hssfRow.getCell(1) || getValue(hssfRow.getCell(1), "class java.lang.String", "").toString().equals("")) {
                    throw new Exception("CELL IS NULL");
                }

                for (int j = 0; j < cellNum; j++) {
                    if (hssfRow.getCell(j) == null) {
                        rlist.add("");
                    } else {
                        String tmp = getValue(hssfRow.getCell(j), "class java.lang.String", "").toString();
                        rlist.add(tmp);
                    }
                }
                list.add(rlist);
            }
        }
        return list;
    }

}
