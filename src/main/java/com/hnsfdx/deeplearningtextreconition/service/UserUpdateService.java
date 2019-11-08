package com.hnsfdx.deeplearningtextreconition.service;

import com.hnsfdx.deeplearningtextreconition.pojo.User;

public interface UserUpdateService {
    //更新用户状态
    boolean updateUser(User user);
}
