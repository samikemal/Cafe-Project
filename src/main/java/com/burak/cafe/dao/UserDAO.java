package com.burak.cafe.dao;

import com.burak.cafe.dto.UserDto;
import com.burak.cafe.entity.UserEntity;

import java.util.List;

public interface UserDAO {
    UserDto findUserByEmail(String email);
    void save(UserEntity user);
    void update(UserEntity userEntity);
    List<UserDto> findAll();
    UserDto findOne(int id);
    void deleteUser(int id);


}
