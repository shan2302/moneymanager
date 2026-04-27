package com.shantanu.moneymanager.repository;

import com.shantanu.moneymanager.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    //Select * from tbl_profiles where email = ?
    Optional<ProfileEntity> findByEmail(String email);

//    Select  * from tbl_profiles where activation_token = ?
    Optional<ProfileEntity> findByActivationToken(String activationToken);
}
