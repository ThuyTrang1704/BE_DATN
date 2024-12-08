package com.example.toeicwebsite.service.impl.userdetailsimpl;


import com.example.toeicwebsite.data.entity.User;
import com.example.toeicwebsite.data.repository.UserRepository;
import com.example.toeicwebsite.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(userName).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("email: ", userName))
        );

        return UserDetailsImpl.build(user);
    }
    //    @Autowired
    //    private PhanQuyenRepository phanQuyenRepository;


}