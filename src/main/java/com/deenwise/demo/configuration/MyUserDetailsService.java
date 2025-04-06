package com.deenwise.demo.configuration;

import com.deenwise.demo.model.UserModel;
import com.deenwise.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> optionalUserModel = userRepository.findByEmail(username);
        if(optionalUserModel.isPresent())
            return new UserPrincipal(optionalUserModel.get());
        else
            return null;
    }
}
