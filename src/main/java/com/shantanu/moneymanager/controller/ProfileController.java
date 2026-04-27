package com.shantanu.moneymanager.controller;

import com.shantanu.moneymanager.dto.AuthDTO;
import com.shantanu.moneymanager.dto.ProfileDTO;
import com.shantanu.moneymanager.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<ProfileDTO> registerProfile(@RequestBody ProfileDTO profileDTO){
        ProfileDTO registeredProfile = profileService.registerProfile(profileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredProfile);
    }
    @GetMapping("/activate")
    public ResponseEntity<String> activateProfile(@RequestParam String token){
        boolean isActivated = profileService.activateProfile(token);
        if(isActivated){
            return ResponseEntity.ok("Profile activated successfully");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activation Token not found or already used");
        }
    }
    @PostMapping("/login")
    public  ResponseEntity<Map<String,Object>> login(@RequestBody AuthDTO authDTO) {
        System.out.println("Login attempt for: " + authDTO.getEmail() + " with pass: " + authDTO.getPassword());
        try {
            System.out.println("Is Account Active or not :- "+ profileService.isAccountActive(authDTO.getEmail()));
            if(!profileService.isAccountActive(authDTO.getEmail()))
            {

                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                        "message", "Account is not active . Please activate your account first"
                ));
            }

            Map<String, Object> response  =profileService.authenaticeAndGenerateToken(authDTO);
            System.out.println("response "+response);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message" , e.getMessage()
            ));
        }
    }

}


/