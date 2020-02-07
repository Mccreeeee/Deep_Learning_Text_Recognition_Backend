package com.hnsfdx.deeplearningtextrecognition.mapper;

import com.hnsfdx.deeplearningtextrecognition.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserMapper {
    User findByLoginName(String loginName);

    boolean saveUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(String loginName);

    User findLoginUser(Map<String, String> loginMap);
}
