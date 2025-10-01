package com.showcaseai.service;

import com.showcaseai.model.User;
import com.showcaseai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User findByProfileSlug(String slug) {
        return userRepository.findByProfileSlug(slug);
    }
}
