package com.imooc.o2o.service;

import com.imooc.o2o.dto.LocalAuthExecution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.exceptions.LocalAuthOperationException;

public interface LocalAuthService {
    /**
     * 登入
     * @Author: l5125
     * @Date: 2020/6/30
     * @param username:
     * @param password:
     * @return: com.imooc.o2o.entity.LocalAuth
     **/
    LocalAuth login(String username,String password);

    /**
     * 修改密碼的方法
     * @Author: l5125
     * @Date: 2020/6/30
     * @param userId:
     * @param username:
     * @param password:
     * @param newPassword:
     * @return: com.imooc.o2o.dto.LocalAuthExecution
     **/
    LocalAuthExecution modifyLocalAuth(Long userId, String username, String password
            , String newPassword)throws LocalAuthOperationException;

    /**
     * 用userId查用戶
     * @Author: l5125
     * @Date: 2020/6/30
     * @param userId:
     * @return: com.imooc.o2o.entity.LocalAuth
     **/
    LocalAuth queryLocalByUserId(Long userId);
}
