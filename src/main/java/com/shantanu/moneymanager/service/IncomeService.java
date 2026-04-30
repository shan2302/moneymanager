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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    //  Delete income by id for current user
    public void deleteIncome(Long incomeId)
    {
        ProfileEntity profile = profileService.getCurrentProfile();
        IncomeEntity entity = incomeRepository.findById(incomeId)
                .orElseThrow(() -> new RuntimeException("Income not found"));
        if(!entity.getProfile().getId().equals(profile.getId()))
        {
            throw new RuntimeException("Unauthorized to delete this epense");
        }
        incomeRepository.delete(entity);
    }

    //    Get latest 5 income for current user
    public List<IncomeDTO> getLatest5IncomeForCurrentUser(){
        ProfileEntity profile = profileService.getCurrentProfile();
        List<IncomeEntity> list =incomeRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
        return list.stream().map(this::toDTO).toList();
    }

    //    Get Total income for current user
    public BigDecimal getTotalIncomesForCurrentUser(){
        ProfileEntity profile = profileService.getCurrentProfile();
        BigDecimal total = incomeRepository.findTotalIncomeByProfileId(profile.getId());
        return total != null ? total : BigDecimal.ZERO;
    }
    //    Filter Incomes
    public List<IncomeDTO> filterIncomes(LocalDate startDate, LocalDate endDate, String keyword, Sort sort)
    {
        ProfileEntity profile= profileService.getCurrentProfile();
        List<IncomeEntity> list =incomeRepository.findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(profile.getId(),startDate,endDate,keyword,sort);
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
