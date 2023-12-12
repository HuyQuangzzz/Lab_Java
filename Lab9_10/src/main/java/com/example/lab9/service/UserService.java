package com.example.lab9.service;

import java.util.List;
import java.util.Optional;

import com.example.lab9.model.CustomUserDetails;
import com.example.lab9.model.User;
import com.example.lab9.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class UserService implements UserDetailsService{
    
    @Autowired
    private UserRepository repo;

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public Optional<User> getUserById(long id) {
        return repo.findById(id);
    }

    public void addUser(User user) {
        repo.save(user);
    }

    public void updateUser(User user) {
        repo.save(user);
    }

    public void deleteUser(User user) {
       repo.delete(user);
    }

    public User getUserByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException(email);

        }

        return new CustomUserDetails(user);
    }

}