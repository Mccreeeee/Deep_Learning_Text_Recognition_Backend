package com.hnsfdx.deeplearningtextreconition.repository;

import com.hnsfdx.deeplearningtextreconition.pojo.User;

public interface UserRepository {
    User findByLoginName(String loginName);
}
