package com.zzqfsy.account.test.account.dao;

import com.zzqfsy.account.dao.TAccountDAO;
import com.zzqfsy.account.domain.Account;
import com.zzqfsy.account.test.base.BaseMockConfigTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: 荀凡
 * @Description:
 * @date: Created in 10:11 AM 2019/1/2
 * @modified By:
 **/
public class TAccountDAOTest extends BaseMockConfigTest {

    @Autowired
    private TAccountDAO tAccountDAO;

    @Test
    public void get(){
        Account account = tAccountDAO.selectByPrimaryKey(2);
        Assert.assertNotNull(account);
    }

    @Test
    public void getMock(){
        Account account = tAccountDAO.selectByPrimaryKey(3);
        Assert.assertNull(account);
    }
}
