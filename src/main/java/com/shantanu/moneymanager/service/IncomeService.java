package com.shantanu.moneymanager.service;


import com.shantanu.moneymanager.dto.ExpenseDTO;
import com.shantanu.moneymanager.dto.IncomeDTO;
import com.shantanu.moneymanager.entity.CategoryEntity;
import com.shantanu.moneymanager.entity.ExpenseEntity;
import com.shantanu.moneymanager.entity.IncomeEntity;
import com.shantanu.moneymanager.entity.ProfileEntity;
import com.shantanu.moneymanager.repository.CategoryRepository;
import com.shantanu.moneymanager.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {
    private final CategoryRepository categoryRepository;
    private  final IncomeRepository incomeRepository;
    private final ProfileService profileService;

    public IncomeDTO addIncome(IncomeDTO dto) {
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity category =  categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        IncomeEntity newExpense = toEntity(dto,profile,category);
        newExpense = incomeRepository.save(newExpense);
        return toDTO(newExpense);

    }
    //    Retrieves all income for the current month/based on the start date and end date
    public List<IncomeDTO> getCurrentMonthIncomeForCurrentUser(){
        ProfileEntity profile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate =  now.withDayOfMonth(now.lengthOfMonth());
        List<IncomeEntity> list =  incomeRepository.findByProfileIdAndDateBetween(profile.getId(),startDate,endDate);
        return list.stream().map(this::toDTO).toList();
    }

    //    Helper methods
    private IncomeEntity toEntity(IncomeDTO dto, ProfileEntity profile, CategoryEntity category)
    {
        return IncomeEntity.builder()
                .name(dto.getName())
                .icon(dto.getIcon())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .profile(profile)
                .category(category)
                .build();
    }

    private IncomeDTO toDTO (IncomeEntity entity)
    {
        return IncomeDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .icon(entity.getIcon())
                .categoryId(entity.getCategory()!= null ? entity.getCategory().getId():null)
                .categoryName(entity.getCategory()!= null ? entity.getCategory().getName(): "N/A")
                .amount(entity.getAmount())
                .date(entity.getDate())
                .createdAt(entity.getCreatedAt())
                .UpdatedAt(entity.getUpdatedAt())
                .build();
    }
}
