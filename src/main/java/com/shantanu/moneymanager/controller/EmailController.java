package com.shantanu.moneymanager.controller;

import com.shantanu.moneymanager.dto.ExpenseDTO;
import com.shantanu.moneymanager.dto.IncomeDTO;
import com.shantanu.moneymanager.service.EmailService;
import com.shantanu.moneymanager.service.ExcelService;
import com.shantanu.moneymanager.service.ExpenseService;
import com.shantanu.moneymanager.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;
    private final ExcelService excelService;
    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    @GetMapping("/income-excel")
    public ResponseEntity<Void> emailIncomeExcel()  throws IOException {
        try {
            // 1. Get current month data
            List<IncomeDTO> incomes = incomeService.getCurrentMonthIncomeForCurrentUser();

            // 2. Generate the Excel content in memory
            byte[] excelContent = excelService.generateIncomeExcel(incomes);

            // 3. Get current logged in user's email
            String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

            // 4. Send the email
            emailService.sendEmailWithAttachment(
                    currentUserEmail,
                    "Monthly Income Report",
                    "Hello, please find your monthly income details attached as an Excel file.",
                    excelContent,
                    "income_report.xlsx"
            );

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/expense-excel")
    public ResponseEntity<Void> emailExpenseExcel(){
        try {
            List<ExpenseDTO> expenses = expenseService.getCurrentMonthExpensesForCurrentUser();
            byte[] excelContent = excelService.generateExpenseExcel(expenses);
            String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

            emailService.sendEmailWithAttachment(
                    currentUserEmail,
                    "Monthly Expense Report",
                    "Hello, Please find your monthly expense details attached",
                    excelContent,
                    "expense_report.xlsx"
            );
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}