package com.hyl.service;

import com.hyl.pojo.userEntity;

public interface userService {

    public userEntity findById(int id);

    public userEntity updateEntity(userEntity user1);

    public int delete(int id);
}
