package com.imooc.o2o.dao;

import com.imooc.o2o.entity.LocalAuth;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface LocalAuthDao {

    /**
     * 用帳密拿用戶
     * @param username:
     * @param password:
     * @Author: l5125
     * @Date: 2020/6/30
     * @return: com.imooc.o2o.entity.LocalAuth
     **/
    LocalAuth queryLocalByUserNameAndPwd(@Param("username") String username
            , @Param("password") String password);

    /**
     * 用userId查localauth
     * @Author: l5125
     * @Date: 2020/6/30
     * @param userId:
     * @return: com.imooc.o2o.entity.LocalAuth
     **/
    LocalAuth queryLocalByUserId(Long userId);

    /**
     * 更新密碼
     * @param username:
     * @param password:
     * @Author: l5125
     * @Date: 2020/6/30
     * @return: com.imooc.o2o.entity.LocalAuth
     **/
    int updateLocalAuth(@Param("userId") Long userId, @Param("username") String username
            , @Param("password") String password, @Param("newPassword") String newPassword
            , @Param("lastEditTime") Date lastEditTime);


}
