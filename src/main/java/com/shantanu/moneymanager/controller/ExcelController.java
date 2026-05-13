package com.shantanu.moneymanager.controller;

import com.shantanu.moneymanager.dto.ExpenseDTO;
import com.shantanu.moneymanager.dto.IncomeDTO;
import com.shantanu.moneymanager.service.ExcelService;
import com.shantanu.moneymanager.service.ExpenseService;
import com.shantanu.moneymanager.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    private final ExcelService excelService;
    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    public ExcelController(ExcelService excelService, IncomeService incomeService, ExpenseService expenseService) {
        this.excelService = excelService;
        this.incomeService = incomeService;
        this.expenseService = expenseService;
    }

    @GetMapping("/download/income")
    public ResponseEntity<byte[]> downloadIncomeExcel() throws IOException{
        List<IncomeDTO> incomes = incomeService.getCurrentMonthIncomeForCurrentUser();
        byte[] excelContent = excelService.generateIncomeExcel(incomes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=income_details.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excelContent);
    }
    @GetMapping("/download/expense")
    public  ResponseEntity<byte[]> downloadExpenseExcel() throws IOException {
        List<ExpenseDTO> expenses = expenseService.getCurrentMonthExpensesForCurrentUser();
        byte[] excelContent = excelService.generateExpenseExcel(expenses);

        return  ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;fileename=expense_details.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excelContent);
    }
}
