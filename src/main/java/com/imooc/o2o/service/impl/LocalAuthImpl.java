package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.LocalAuthDao;
import com.imooc.o2o.dto.LocalAuthExecution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.enums.LocalAuthStateEnum;
import com.imooc.o2o.exceptions.LocalAuthOperationException;
import com.imooc.o2o.service.LocalAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("localAuth")
public class LocalAuthImpl implements LocalAuthService {

    @Autowired
    private LocalAuthDao localAuthDao;

    @Override
    public LocalAuth login(String username, String password) {
        return localAuthDao.queryLocalByUserNameAndPwd(username, password);
    }

    @Override
    @Transactional
    public LocalAuthExecution modifyLocalAuth(Long userId, String username, String password
            , String newPassword) throws LocalAuthOperationException {
        //判斷傳入的有無值,密碼有無變更
        if (userId != null && username != null && password != null && newPassword != null
        && !password.equals(newPassword)) {
            try {
                int effectedNum = localAuthDao.updateLocalAuth(userId, username, password
                        , newPassword, new Date());
                if (effectedNum <= 0) {
                    throw new LocalAuthOperationException("更新密碼失敗");
                }
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);

            } catch (Exception e) {
                throw new LocalAuthOperationException("更新密碼失敗" + e.getMessage());
            }
        } else {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
    }

    @Override
    public LocalAuth queryLocalByUserId(Long userId) {
        return localAuthDao.queryLocalByUserId(userId);
    }


}
