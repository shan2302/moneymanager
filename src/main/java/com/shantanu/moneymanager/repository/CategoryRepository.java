package com.shantanu.moneymanager.repository;

import com.shantanu.moneymanager.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.SQLType;
import java.util.List;
import java.util.Optional;

public interface  CategoryRepository extends JpaRepository<CategoryEntity,Long> {
//    Selecty * from tbl_categories where profile_id = ?1
    List <CategoryEntity> findByProfile_Id(Long profileId);

//    Select * from tbl_categories where id= ?1 and profile_id = ?2
    Optional<CategoryEntity> findByIdAndProfile_Id(Long id, Long profileId);

//    Select * from tbl_categories where type = ?1 and profile_id = ?2
    List<CategoryEntity> findByTypeAndProfile_Id(String type,Long profileId);

    Boolean existsByNameAndProfile_Id(String name,Long profileId);
}
