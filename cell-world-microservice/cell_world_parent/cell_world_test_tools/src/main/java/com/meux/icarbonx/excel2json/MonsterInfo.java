package com.meux.icarbonx.excel2json;


import com.alibaba.fastjson.JSON;
import lombok.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonsterInfo {
    public int id;
    public List<PveGridInfo> gridList = new ArrayList<>();


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class PveGridInfo {

        public int id;
        public int x;
        public int y;
        public int type;
        public int element;
    }

    static class FileSuffixFilter implements FilenameFilter {
        private String type;

        private FileSuffixFilter(String tp) {
            this.type = tp;
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(type);
        }
    }

    public static void main(String[] args) throws IOException {

        String dirPath = "D:\\pve\\excel";
        String targetPath = "D:\\pve\\json";
        File fold = new File(dirPath);
        if (fold.exists()) {
            String[] excelfiles = fold.list(new FileSuffixFilter(".xlsx"));//文件名过滤器
            if (excelfiles == null) return;
            for (String file : excelfiles) {
                String excelfile = dirPath + "/" + file;
                File excel = new File(excelfile);

                if (!excel.isHidden() && !excel.getName().contains("~")) {
                    InputStream in = new FileInputStream(excel);
                    XSSFWorkbook workbook = new XSSFWorkbook(in);

                    Iterator<Sheet> its = workbook.sheetIterator();
                    while (its.hasNext()) {
                        Sheet sheet = its.next();
                        MonsterInfo mon = new MonsterInfo();
                        String sheetName = sheet.getSheetName();
                        mon.setId(Integer.parseInt(sheetName));
                        if (null == mon.getGridList())
                            mon.setGridList(new ArrayList<>());
                        Iterator<Row> it = sheet.rowIterator();
                        PveGridInfo info;
                        int m = 0;
                        while (it.hasNext()) {
                            Row row = it.next();
                            m++;
                            if (m < 4) continue;
//                            Cell cell = row.getCell(0);
//                            if(cell.getCellType() == CellType.STRING) continue;

                            info = new PveGridInfo();

                            for (int i = 0; i < 5; i++) {
                                Cell cell_i = row.getCell(i);
                                if (cell_i.getCellType() == CellType.NUMERIC) {
                                    Double cellValue = cell_i.getNumericCellValue();
                                    if (i == 0) {
                                        info.id = cellValue.intValue();
                                    } else if (i == 1) {
                                        info.x = cellValue.intValue();
                                    } else if (i == 2) {
                                        info.y = cellValue.intValue();
                                    } else if (i == 3) {
                                        info.type = cellValue.intValue();
                                    } else {
                                        info.element = cellValue.intValue();
                                    }
                                }
                            }
                            mon.getGridList().add(info);
                        }
                        String jsonString = JSON.toJSONString(mon);
                        byte[] bytes = jsonString.getBytes();
                        FileCopyUtils.copy(bytes, new File(targetPath + "/" + sheetName + ".bytes"));
                    }

                    in.close();
                }
            }
        }
    }


}
