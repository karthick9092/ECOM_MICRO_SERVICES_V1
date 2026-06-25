package com.example.user.service;

import com.example.user.dto.UserDto;
import org.springframework.stereotype.Service;

public interface UserService {

    public UserDto createUser(UserDto userDto);

    UserDto getUser(Integer id);

    UserDto updateUser(UserDto userDto);

    void deleteUser(int id);
}
