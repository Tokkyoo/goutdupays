package com.goutdupays.goutdupays.controller;

import com.goutdupays.goutdupays.dto.ProfilDto;
import com.goutdupays.goutdupays.modele.User;
import com.goutdupays.goutdupays.payload.UserInfoResponse;
import com.goutdupays.goutdupays.repository.UserRepository;
import com.goutdupays.goutdupays.security.JwtUtils;
import com.goutdupays.goutdupays.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profil")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
public class ProfilController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

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
    public ResponseEntity<?> updateUserById(@PathVariable(value = "userId") Long userId, @RequestBody ProfilDto profilDto) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        cleanEmptyFields(profilDto);
        updateUserDetails(user, profilDto);

        userRepository.save(user);

        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));

    }

    private void cleanEmptyFields(ProfilDto profilDto) {
        if (profilDto.getFirstName() != null && profilDto.getFirstName().isEmpty()) {
            profilDto.setFirstName(null);
        }
        if (profilDto.getLastName() != null && profilDto.getLastName().isEmpty()) {
            profilDto.setLastName(null);
        }
        if (profilDto.getUsername() != null && profilDto.getUsername().isEmpty()) {
            profilDto.setUsername(null);
        }
        if (profilDto.getDescription() != null && profilDto.getDescription().isEmpty()) {
            profilDto.setDescription(null);
        }
        if (profilDto.getEmail() != null && profilDto.getEmail().isEmpty()) {
            profilDto.setEmail(null);
        }
        if (profilDto.getPassword() != null && profilDto.getPassword().isEmpty()) {
            profilDto.setPassword(null);
        }
    }

    private void updateUserDetails(User user, ProfilDto profilDto) {
        if (profilDto.getFirstName() != null) {
            user.setFirstname(profilDto.getFirstName());
        }
        if (profilDto.getLastName() != null) {
            user.setLastname(profilDto.getLastName());
        }
        if (profilDto.getUsername() != null) {
            user.setUsername(profilDto.getUsername());
        }
        if (profilDto.getDescription() != null) {
            user.setDescription(profilDto.getDescription());
        }
        if (profilDto.getEmail() != null) {
            user.setEmail(profilDto.getEmail());
        }
        if (profilDto.getPassword() != null) {
            user.setPassword(encoder.encode(profilDto.getPassword()));
        }
    }

}
