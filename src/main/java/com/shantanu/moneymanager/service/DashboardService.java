package com.shantanu.moneymanager.service;

import com.shantanu.moneymanager.dto.ExpenseDTO;
import com.shantanu.moneymanager.dto.IncomeDTO;
import com.shantanu.moneymanager.dto.RecentTransactionDTO;
import com.shantanu.moneymanager.entity.ProfileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Stream.concat;

@Service
@RequiredArgsConstructor

public class DashboardService {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final ProfileService profileService;


    public Map<String,Object> getDashboardData()
    {
        ProfileEntity profile = profileService.getCurrentProfile();
        Map<String,Object> returnValue = new LinkedHashMap<>();
        List<IncomeDTO> LatestIncomes = incomeService.getLatest5IncomeForCurrentUser();
        List<ExpenseDTO> LatestExpenses = expenseService.getLatest5ExpensesForCurrentUser();
        List<RecentTransactionDTO> recentTransactions = concat(LatestIncomes.stream().map(income ->
                RecentTransactionDTO.builder()
                        .id(income.getId())
                        .profileId(profile.getId())
                        .name(income.getName())
                    .amount(income.getAmount())
                        .date(income.getDate())
                        .createdAt(income.getCreatedAt())
                        .updatedAt(income.getUpdatedAt())
                        .type("income")

                        .build()),
                LatestExpenses.stream().map(expense ->
                        RecentTransactionDTO.builder()
                                .id(expense.getId())
                                .profileId(profile.getId())
                                .icon(expense.getIcon())
                                .name(expense.getName())
                                .amount(expense.getAmount())
                                .date(expense.getDate())
                                .createdAt(expense.getCreatedAt())
                                .updatedAt(expense.getUpdatedAt())
                                .type("expense")
                                .build()))
                .sorted((a,b)-> {
                    int cmp = b.getDate().compareTo(a.getDate());
                    if(cmp == 0 && a.getCreatedAt()!=null && b.getCreatedAt() !=null){
                        return b.getCreatedAt().compareTo(a.getCreatedAt());
                    }
                    return cmp;
                }).collect(Collectors.toList());

        returnValue.put("totalBalance",
                incomeService.getTotalIncomesForCurrentUser()
                        .subtract(expenseService.getTotalExpenseForCurrentUser()));

        returnValue.put("totalIncome",incomeService.getTotalIncomesForCurrentUser());
        returnValue.put("totalExpense",expenseService.getTotalExpenseForCurrentUser());
        returnValue.put("Recent5Expenses",expenseService.getLatest5ExpensesForCurrentUser());
        returnValue.put("Recent5Incomes",LatestIncomes);
        returnValue.put("RecentTransactions",recentTransactions);
        return  returnValue;
    }
}
