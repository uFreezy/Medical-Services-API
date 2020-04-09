package com.jws.medicalfile.api.service;

import com.jws.medicalfile.api.models.User;
import com.jws.medicalfile.api.repository.RoleRepository;
import com.jws.medicalfile.api.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public boolean delete(User user) {
        this.userRepository.deleteById(user.getId());

        return !this.userRepository.findById(user.getId()).isPresent();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public User getbyIndex(int indx) {
        return this.userRepository.findAll().get(indx);
    }

    @Override
    public List<User> findByRole(String roleName) {
        return userRepository.findByRole(roleRepository.findByName(roleName));
    }

    @Override
    public boolean any() {
        return userRepository.count() > 0;
    }

    @Override
    public long count() {
        return userRepository.count();
    }
}
