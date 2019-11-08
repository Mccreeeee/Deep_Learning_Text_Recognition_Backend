package com.hnsfdx.deeplearningtextreconition.mapper;

import com.hnsfdx.deeplearningtextreconition.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByLoginName(String loginName);
    boolean saveUser(User user);
    User updateUser(User user);
}
