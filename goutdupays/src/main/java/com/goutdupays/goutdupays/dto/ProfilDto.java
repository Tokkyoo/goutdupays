package com.goutdupays.goutdupays.dto;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ProfilDto {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String description;
}
