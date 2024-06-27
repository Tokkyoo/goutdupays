package com.goutdupays.goutdupays.controller;

import com.goutdupays.goutdupays.dto.ProfilDto;
import com.goutdupays.goutdupays.modele.User;
import com.goutdupays.goutdupays.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/profil")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
public class ProfilController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ProfilDto> getUserById(@PathVariable(value = "userId") Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ProfilDto profil = new ProfilDto();
        profil.setId(userOptional.get().getId());
        profil.setFirstName(userOptional.get().getFirstname());
        profil.setLastName(userOptional.get().getLastname());
        profil.setUsername(userOptional.get().getUsername());
        profil.setDescription(userOptional.get().getDescription());
        profil.setEmail(userOptional.get().getEmail());

        return new ResponseEntity<>(profil, HttpStatus.OK);
    }

    @PatchMapping("/update/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ProfilDto> updateUserById(@PathVariable(value = "userId") Long userId, @RequestBody ProfilDto profilDto) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        userOptional.get().setFirstname(profilDto.getFirstName());
        userOptional.get().setLastname(profilDto.getLastName());
        userOptional.get().setUsername(profilDto.getUsername());
        userOptional.get().setDescription(profilDto.getDescription());
        userOptional.get().setEmail(profilDto.getEmail());
        userOptional.get().setPassword(encoder.encode(profilDto.getPassword()));

        userRepository.save(userOptional.get());

        return new ResponseEntity<>(profilDto, HttpStatus.OK);
    }
}
