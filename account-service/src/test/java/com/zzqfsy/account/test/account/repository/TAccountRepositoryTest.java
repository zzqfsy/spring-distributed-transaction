package com.zzqfsy.account.test.account.repository;

import com.zzqfsy.account.dao.TAccountDAO;
import com.zzqfsy.account.dao.TAccountFlowDAO;
import com.zzqfsy.account.manager.LockOnRedisManager;
import com.zzqfsy.account.repository.AccountRepository;
import com.zzqfsy.account.test.base.BaseMockConfigTest;
import com.zzqfsy.account.test.base.BaseTest;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 荀凡
 * @Description:
 * @date: Created in 10:22 AM 2019/1/2
 * @modified By:
 **/
public class TAccountRepositoryTest extends BaseTest {

    @Spy
    @InjectMocks
    //@Autowired
    private AccountRepository accountRepository;

    @Spy
    @InjectMocks
    private LockOnRedisManager lockOnRedisManager;

    @Mock
    private RedissonClient redissonClient;
    @Mock
    private RLock rLock;

    @Autowired
    private TAccountDAO tAccountDAO;
    @Autowired
    private TAccountFlowDAO tAccountFlowDAO;

    @Before
    public void stub(){
        String key = AccountRepository.LOCK_KEY_ACCOUNT_BALANCE + "1";
        try {
            Mockito.when(rLock.tryLock( 5, 5, TimeUnit.SECONDS)).thenReturn(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Mockito.when(redissonClient.getLock(key)).thenReturn(rLock);
        //Mockito.when(lockOnRedisManager.getLock(key)).thenReturn(rLock);

        ReflectionTestUtils.setField(accountRepository, "tAccountDAO", tAccountDAO);
        ReflectionTestUtils.setField(accountRepository, "tAccountFlowDAO", tAccountFlowDAO);
        int i = 3;
    }

    @Test
    public void testChangeUserAccountBalance(){
        Pair<Boolean, String> result = accountRepository.changeUserAccountBalance(1, "123", BigDecimal.valueOf(123));
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getKey());
    }
}
