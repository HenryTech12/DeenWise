package com.deenwise.demo.configuration;

import com.deenwise.demo.model.UserModel;
import com.deenwise.demo.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<UserModel> optionalUserModel = userRepository.findByEmail(username);
            return optionalUserModel.map(UserPrincipal::new).orElse(new UserPrincipal());
        }
        catch (UsernameNotFoundException e) {
            log.error("ERROR: {}",e.getMessage());
        }
        catch (NullPointerException nulle) {
            log.error("ERROR: {}",nulle.getMessage());
        }
        return null;
    }
}
