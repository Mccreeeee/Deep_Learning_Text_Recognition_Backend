package com.hnsfdx.deeplearningtextrecognition.repository;

import com.hnsfdx.deeplearningtextrecognition.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserRepository {
    User findByLoginName(String loginName);

    boolean saveUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(String loginName);

    User findLoginUser(Map<String, String> loginMap);

    List<String> findAllAvatarUrl();
}
