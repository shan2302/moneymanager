package com.shantanu.moneymanager.service;

import com.shantanu.moneymanager.dto.ExpenseDTO;
import com.shantanu.moneymanager.dto.IncomeDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ExcelService {

    public byte[] generateIncomeExcel(List<IncomeDTO> incomes) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Incomes");

            // Header Row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Name", "Category", "Amount", "Date"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Data Rows
            int rowIdx = 1;
            for (IncomeDTO income : incomes) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(income.getId());
                row.createCell(1).setCellValue(income.getName());
                row.createCell(2).setCellValue(income.getCategoryName());
                row.createCell(3).setCellValue(income.getAmount().doubleValue());
                row.createCell(4).setCellValue(income.getDate().toString());
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }
    public byte[] generateExpenseExcel(List<ExpenseDTO> expenses) throws  IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()){
            Sheet sheet = workbook.createSheet("Expenses");

            //Header Row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID","Name","Category","Amount","Date"};
            for(int i=0;i<columns.length;i++){
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            //Data Rows
            int rowIdx = 1;
            for(ExpenseDTO expense:expenses){
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(expense.getId());
                row.createCell(1).setCellValue(expense.getName());
                row.createCell(2).setCellValue(expense.getCategoryName());
                row.createCell(3).setCellValue(expense.getAmount().doubleValue());
                row.createCell(4).setCellValue(expense.getDate().toString());
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }


}



