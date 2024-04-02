package com.goutdupays.goutdupays.dto;



import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class SignUpDto {
    private Set<String> role;
    private String username;
    private String email;
    private String password;




}