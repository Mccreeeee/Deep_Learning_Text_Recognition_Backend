package com.hnsfdx.deeplearningtextreconition.service;

import com.hnsfdx.deeplearningtextreconition.pojo.User;

public interface UserLoginService {
    //验证用户是否存在
    boolean verifyUser(User user);
    //头像下载

}
