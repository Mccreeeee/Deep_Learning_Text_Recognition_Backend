package com.hnsfdx.deeplearningtextrecognition.mapper;

import com.hnsfdx.deeplearningtextrecognition.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    User findByLoginName(@Param(value = "loginName") String loginName);

    boolean saveUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(@Param(value = "loginName") String loginName);

    User findLoginUser(Map<String, String> loginMap);

    List<String> findAllAvatarUrl();
}
