package com.irdaislakhuafa.simplespringbootjwt.services;

import java.util.List;
import java.util.Optional;

import com.irdaislakhuafa.simplespringbootjwt.model.dtos.UserDto;
import com.irdaislakhuafa.simplespringbootjwt.model.entities.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements BaseService<User, UserDto> {

    @Override
    public Optional<User> save(User entity) {
        return null;
    }

    @Override
    public Optional<User> update(User entity) {
        return null;
    }

    @Override
    public Optional<User> findById(String id) {
        return null;
    }

    @Override
    public boolean isExistsById(String id) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User mapToEntity(UserDto dto) throws Exception {
        return null;
    }

    @Override
    public List<User> mapToEntities(List<UserDto> dtos) throws Exception {
        return null;
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }

}
