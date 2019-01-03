package com.zzqfsy.account.repository;

import com.zzqfsy.account.dao.TAccountDAO;
import com.zzqfsy.account.dao.TAccountFlowDAO;
import com.zzqfsy.account.domain.Account;
import com.zzqfsy.account.domain.AccountFlow;
import com.zzqfsy.account.manager.LockOnRedisManager;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 8:26 2018/8/17
 * @Modified By:
 **/
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
public class AccountRepository {

    @Autowired
    private TAccountDAO tAccountDAO;
    @Autowired
    private TAccountFlowDAO tAccountFlowDAO;
    @Autowired
    private LockOnRedisManager lockOnRedisManager;

    /**
     * 获取用户账户信息
     * @param userId
     * @return
     */
    public Account getUserAccountByUserId(Integer userId){
        Account temp1 = new Account();
        temp1.setIUserId(userId);
        return tAccountDAO.selectOne(temp1);
    }

    public static final String LOCK_KEY_ACCOUNT_BALANCE = "lock:term:account:balance:";
    /**
     * 更改账户余额
     * @param userId
     * @param changeAmount
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Pair<Boolean, String> changeUserAccountBalance(Integer userId, String orderNo, BigDecimal changeAmount){
        String key = LOCK_KEY_ACCOUNT_BALANCE + userId;
        Pair pair = lockOnRedisManager.handleByUnfair(key, 5, 5, Pair.class, () -> {

            Account userAccount = this.getUserAccountByUserId(userId);
            if (userAccount == null || userAccount.getId() <= 0) {
                return new Pair(false, "账户不存在");
            }

            BigDecimal beforeAmount = userAccount.getDcmBalance();
            BigDecimal afterAmount = userAccount.getDcmBalance().add(changeAmount);

            Account temp2 = new Account();
            temp2.setId(userAccount.getId());
            temp2.setDcmBalance(afterAmount);
            tAccountDAO.updateByPrimaryKeySelective(temp2);

//            int i = 1 / 0;

            AccountFlow accountFlow = new AccountFlow();
            accountFlow.setIUserId(userAccount.getIUserId());
            accountFlow.setDcmBalanceBefore(beforeAmount);
            accountFlow.setDcmBalanceChange(changeAmount);
            accountFlow.setDcmBalanceAfter(afterAmount);
            accountFlow.setIStatus(1);
            accountFlow.setSOrderNo(orderNo);
            tAccountFlowDAO.insertSelective(accountFlow);

            return new Pair(true, null);
        });
        if (pair == null)
            return new Pair<>(false, "服务繁忙，请稍后访问");

        return pair;
    }
}
