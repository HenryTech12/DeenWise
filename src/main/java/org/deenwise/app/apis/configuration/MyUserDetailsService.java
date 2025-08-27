package org.deenwise.app.apis.configuration;

import lombok.extern.slf4j.Slf4j;
import org.deenwise.app.apis.dto.UserDTO;
import org.deenwise.app.apis.exception.UserNotFoundException;
import org.deenwise.app.apis.model.UserModel;
import org.deenwise.app.apis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserModel userModel = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User With email: %s Not Found.",username)));

        UserDTO userDTO = new UserDTO(userModel.getId(),userModel.getFullname(),userModel.getEmail(),userModel.getPassword());
        return new UserPrincipal(userDTO,userModel.getUserRole());
    }
}
