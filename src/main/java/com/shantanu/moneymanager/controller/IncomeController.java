package com.shantanu.moneymanager.controller;

import com.shantanu.moneymanager.dto.ExpenseDTO;
import com.shantanu.moneymanager.dto.IncomeDTO;
import com.shantanu.moneymanager.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/incomes")
public class IncomeController {

     private final IncomeService incomeService;
    @PostMapping
    public ResponseEntity<IncomeDTO> addIncome(@RequestBody IncomeDTO dto){
        System.out.println("Inside The addIncome");
        IncomeDTO saved = incomeService.addIncome(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping
    public ResponseEntity<List<IncomeDTO>> getExpenses()
    {
        List<IncomeDTO> incomes = incomeService.getCurrentMonthIncomeForCurrentUser();
        return ResponseEntity.ok(incomes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }
}
