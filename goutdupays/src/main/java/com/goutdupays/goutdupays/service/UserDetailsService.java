package com.goutdupays.goutdupays.service;

import com.goutdupays.goutdupays.modele.User;
import com.goutdupays.goutdupays.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}