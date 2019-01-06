package com.zzqfsy.account.test.account.dao;

import com.zzqfsy.account.dao.TAccountDAO;
import com.zzqfsy.account.domain.Account;
import com.zzqfsy.account.test.base.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: zzqfsy
 * @Description:
 * @date: Created in 10:11 AM 2019/1/2
 * @modified By:
 **/
public class TAccountDAOTest extends BaseTest {

    @Autowired
    private TAccountDAO tAccountDAO;

    @Test
    public void accountExist(){
        Account account = tAccountDAO.selectByPrimaryKey(2);
        Assert.assertNotNull(account);
    }

    @Test
    public void accountNotExist(){
        Account account = tAccountDAO.selectByPrimaryKey(3);
        Assert.assertNull(account);
    }
}
