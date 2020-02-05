package com.hnsfdx.deeplearningtextreconition.repository.impl;

import com.hnsfdx.deeplearningtextreconition.mapper.UserMapper;
import com.hnsfdx.deeplearningtextreconition.pojo.User;
import com.hnsfdx.deeplearningtextreconition.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByLoginName(String loginName) {
        return userMapper.findByLoginName(loginName);
    }

    @Override
    public boolean saveUser(User user) {
        return userMapper.saveUser(user);
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public boolean deleteUser(String loginName) {
        return userMapper.deleteUser(loginName);
    }

    @Override
    public User findLoginUser(Map<String, String> loginMap) {
        return userMapper.findLoginUser(loginMap);
    }

}
