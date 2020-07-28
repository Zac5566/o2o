package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.LocalAuth;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class LocalAuthDaoTest extends BaseTest {

    @Autowired
    private LocalAuthDao localAuthDao;

    @Test
    @Ignore
    public void testAQueryLocalByUserNameAndPwd(){
        LocalAuth findLocalAuth = localAuthDao.queryLocalByUserNameAndPwd("testbind","s05bse6q2qlb9qblls96s592y55y556s");
        assertEquals("testbind",findLocalAuth.getUsername());
    }
    @Test
    @Ignore
    public void testBUpdateUser(){
        Long userId = 1L;
        String username = "testbind";
        String password = "s05bse6q2qlb9qblls96s592y55y556s";
        String newPassword = "123";
        Date lastEditTime = new Date();
        int effectedNum = localAuthDao.updateLocalAuth(userId, username, password, newPassword,lastEditTime);
        assertEquals(1,effectedNum);
    }
    @Test
    public void testCQueryLocalByUserId(){
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
        assertEquals("testbind",localAuth.getUsername());
    }
}
