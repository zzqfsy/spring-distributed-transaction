package com.zzqfsy.entry.controller;

import com.zzqfsy.api.resp.BaseResp;
import com.zzqfsy.api.rpc.IEntryFacade;
import com.zzqfsy.entry.proxy.AccountProxy;
import com.zzqfsy.entry.proxy.OrderProxy;
import com.zzqfsy.entry.proxy.ProjectProxy;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 15:45 2018/8/21
 * @Modified By:
 **/
@RestController
@RequestMapping(value = "/entry")
public class EntryController implements IEntryFacade {

    @RequestMapping(value = "/project/buy", method = RequestMethod.GET)
    public BaseResp test(){
        return BaseResp.getInstance();
    }

    @RequestMapping(value = "/project/buy", method = RequestMethod.POST)
    @Override
    public BaseResp buyProject(@RequestParam("projectId") String projectId, @RequestParam("userId") String userId,
                               @RequestParam("amount") String amount){
        if (StringUtils.isEmpty(projectId) ||
                StringUtils.isEmpty(userId) ||
                StringUtils.isEmpty(amount)){
            return BaseResp.getFailInstance("参数错误");
        }
        BaseResp baseResp;

        // step 1.1; 检查账户余额
        BigDecimal userAccountBalance;
        baseResp = AccountProxy.getUserAccountBalance(userId);
        if (!baseResp.isSuccess()){
            return baseResp;
        }
        if ((userAccountBalance = (BigDecimal)baseResp.getResult()).compareTo(BigDecimal.ZERO) <= 0 ||
                userAccountBalance.compareTo(new BigDecimal(amount)) < 0){
            return BaseResp.getFailInstance("账户余额不足");
        }

        // step 1.2; 减少库存
        baseResp = ProjectProxy.changProject(projectId, amount);
        if (!baseResp.isSuccess()){
            return baseResp;
        }
        // step 1.3: 创建订单
        baseResp = OrderProxy.creteOrder(projectId, userId, amount);
        if (!baseResp.isSuccess()){
            // 回退库存
            ProjectProxy.changProject(projectId, BigDecimal.ZERO.subtract(new BigDecimal(amount)).toPlainString());
            return baseResp;
        }
        String orderNo = baseResp.getResult().toString();

        // step 2.2; 更新订单状态
        baseResp = OrderProxy.updateOrder2CompletedByOrderNo(orderNo);
        if (!baseResp.isSuccess()){
            // 回退库存
            ProjectProxy.changProject(projectId, BigDecimal.ZERO.subtract(new BigDecimal(amount)).toPlainString());
            return baseResp;
        }

        // step 2.3: 账户扣款
        baseResp = AccountProxy.changeUserAccountBalance(userId, orderNo, amount);
        if (!baseResp.isSuccess()){
            // 回退库存
            ProjectProxy.changProject(projectId, BigDecimal.ZERO.subtract(new BigDecimal(amount)).toPlainString());
            // 更新订单失败状态
            baseResp = OrderProxy.updateOrder2CanceledByOrderNo(orderNo);
            return baseResp;
        }

        return BaseResp.getInstance("购买成功");
    }

    @RequestMapping(value = "/project/buy/mq", method = RequestMethod.POST)
    @Override
    public BaseResp buyProjectMQ(String userId, String projectId, String amount) {
        return null;
    }
}
