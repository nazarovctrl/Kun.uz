package com.example.kunuz.controller;

import com.example.kunuz.dto.auth.LoginDTO;
import com.example.kunuz.dto.auth.LoginResponseDTO;
import com.example.kunuz.dto.profile.ProfileResponseDTO;
import com.example.kunuz.dto.auth.UserRegistrationDTO;
import com.example.kunuz.enums.Language;
import com.example.kunuz.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@Tag(name = "Authorization Controller", description = "This controller for authorization")
public class AuthController {
//    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }


    @Operation(summary = "Method for registration", description = "This method used to create a user")
    @PostMapping("/registration")
    private ResponseEntity<ProfileResponseDTO> registration(@Valid @RequestBody UserRegistrationDTO dto, @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {
        log.info("Registration : email {}, name {}", dto.getEmail(), dto.getName());
        ProfileResponseDTO result = service.registration(dto, language);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Method fro verification", description = "This method used to verifying by email after registration")
    @GetMapping("/verification/email/{jwt}")
    private ResponseEntity<String> verification(@PathVariable("jwt") String jwt) {
        log.info("Verification: jwt {}", jwt);
        String result = service.verification(jwt);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Method for authorization", description = "This method used for Login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO dto, @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {


        log.info(" Login :  email {} " ,dto.getEmail());
        LoginResponseDTO response = service.login(dto, language);
        return ResponseEntity.ok(response);
    }

}
