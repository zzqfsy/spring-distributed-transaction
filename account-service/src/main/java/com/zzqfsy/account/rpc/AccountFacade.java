package com.zzqfsy.account.rpc;

import com.zzqfsy.account.domain.Account;
import com.zzqfsy.account.repository.AccountRepository;
import com.zzqfsy.api.resp.BaseResp;
import com.zzqfsy.api.rpc.IAccountFacade;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 8:29 2018/8/17
 * @Modified By:
 **/
@RestController
@RequestMapping(value = "/account")
public class AccountFacade implements IAccountFacade {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    @Override
    public BaseResp getUserAccountBalance(@PathVariable("userId") String userId) {
        Integer iUserId;
        if (StringUtils.isEmpty(userId) || (iUserId = Integer.valueOf(userId)) <= 0){
            return BaseResp.getFailInstance("参数错误");
        }
        Account account = accountRepository.getUserAccountByUserId(iUserId);
        return BaseResp.getInstance(account == null ? BigDecimal.ZERO : account.getDcmBalance());
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    @Override
    public BaseResp changeUserAccountBalance(@PathVariable("userId") String userId, @RequestParam("orderNo") String orderNo,
                                             @RequestParam("changeAmount") String changeAmount) {
        BigDecimal dcmChangeAmount;
        Integer iUserId;
        if (StringUtils.isEmpty(userId) || (iUserId = Integer.valueOf(userId)) <= 0 ||
                StringUtils.isEmpty(orderNo) ||
                StringUtils.isEmpty(changeAmount) ||
                (dcmChangeAmount = new BigDecimal(changeAmount)) ==  null ||
                BigDecimal.ZERO.equals(dcmChangeAmount)){
            return BaseResp.getFailInstance("参数错误");
        }

        Pair<Boolean, String> result1 = accountRepository.changeUserAccountBalance(iUserId, orderNo, dcmChangeAmount);
        if (!result1.getKey()){
            return BaseResp.getFailInstance(result1.getValue());
        }

        return BaseResp.getInstance("操作成功");
    }
}
