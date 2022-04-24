package com.irdaislakhuafa.simplespringbootjwt.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.irdaislakhuafa.simplespringbootjwt.model.dtos.UserDto;
import com.irdaislakhuafa.simplespringbootjwt.model.entities.User;
import com.irdaislakhuafa.simplespringbootjwt.model.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements BaseService<User, UserDto>, UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<User> save(User entity) {
        log.info("Saving new user");
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        Optional<User> savedUser = Optional.of(userRepository.save(entity));
        log.info("Success saved new user with id: " + savedUser.get().getId());
        return savedUser;
    }

    @Override
    public Optional<User> update(User entity) {
        log.info("Updating user");
        Optional<User> updatedUser = Optional.of(userRepository.save(entity));
        log.info("Success update user");
        return updatedUser;
    }

    @Override
    public Optional<User> findById(String id) {
        Optional<User> user = userRepository.findById(id);
        log.info((user.isPresent())
                ? String.format("Found user with id: %s", id)
                : String.format("User with id: %s not found", id));
        return user;
    }

    @Override
    public boolean isExistsById(String id) {
        boolean isExists = userRepository.existsById(id);
        log.info("User with id: " + id + ((isExists) ? "" : " doesn't") + " exists");
        return isExists;
    }

    @Override
    public List<User> findAll() {
        log.info("Get all data user");
        List<User> users = userRepository.findAll();
        log.info("Found all data user" + ((users.isEmpty() || users.size() <= 0) ? " but data is empty" : ""));
        return users;
    }

    @Override
    public User mapToEntity(UserDto dto) throws Exception {
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getUsername())
                .password(dto.getPassword())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public List<User> mapToEntities(List<UserDto> dtos) throws Exception {
        return dtos.stream().map((dto) -> {
            try {
                return this.mapToEntity(dto);
            } catch (Exception e) {
                log.info("Error while mapping user dto to entity: " + e.getMessage());
            }
            return null;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(String id) {
        boolean status = this.isExistsById(id);
        userRepository.deleteById(id);
        log.info(status ? "Success deleted user with id: " + id : "user with id: " + id + " not found");
        return status;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("User with email: " + email + " not found");
                });
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

}
