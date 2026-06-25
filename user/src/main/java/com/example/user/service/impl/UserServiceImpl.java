package com.example.user.service.impl;

import com.example.user.dto.UserDto;
import com.example.user.entities.UserEntity;
import com.example.user.exception.EmailAlreadyAvailableException;
import com.example.user.repo.UserRepository;
import com.example.user.service.UserService;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    ObjectMapper objectMapper;

    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setMobileNumber(userDto.getMobileNumber());
        userEntity.setCreatedBy(userDto.getName());
        userEntity.setUpdatedBy(userDto.getName());
        try {
            userEntity = userRepository.save(userEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error creating user: " + e.getMessage());
        }

        userDto.setId(userEntity.getId());
        return userDto;
    }

    @Override
    public UserDto getUser(Integer id) {
        return userRepository.findById(id).map((user) -> objectMapper.convertValue(user, UserDto.class)).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        UserEntity userEntity = userRepository.findById(userDto.getId()).orElseThrow(() -> new RuntimeException("User not found with id: " + userDto.getId()));
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setMobileNumber(
                userDto.getMobileNumber());
        userEntity.setCreatedBy(userDto.getName());
        userEntity.setUpdatedBy(userDto.getName());
        userEntity = userRepository.save(userEntity);
        userDto.setId(userEntity.getId());
        return userDto;
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
