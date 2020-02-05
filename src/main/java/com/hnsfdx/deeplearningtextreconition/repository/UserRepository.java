package com.hnsfdx.deeplearningtextreconition.repository;

import com.hnsfdx.deeplearningtextreconition.pojo.User;

import java.util.Map;

public interface UserRepository {
    User findByLoginName(String loginName);

    boolean saveUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(String loginName);

    User findLoginUser(Map<String, String> loginMap);
}
