package com.zzqfsy.order.repository;

import com.zzqfsy.order.dao.TOrderDAO;
import com.zzqfsy.order.domain.Order;
import com.zzqfsy.order.manager.LockOnRedisManager;
import com.zzqfsy.order.utils.SerialNoPrefixEnum;
import com.zzqfsy.order.utils.SerialNoUtils;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 12:46 2018/8/21
 * @Modified By:
 **/
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
public class OrderRepository {

    @Autowired
    private TOrderDAO tOrderDAO;
    @Autowired
    private LockOnRedisManager lockOnRedisManager;

    @Transactional
    public Order createOrder(Integer iProjectId, Integer iUserId, BigDecimal dcmAmount) {
        Order order = new Order();
        order.setIProjectId(iProjectId);
        order.setIUserId(iUserId);
        order.setSOrderNo(SerialNoUtils.generatorSerialNo(SerialNoPrefixEnum.PROJECT_ORDER, iUserId.toString()));
        order.setDcmAmount(dcmAmount.abs());
        order.setIStatus(0);

        tOrderDAO.insertSelective(order);

        return order;
    }

    @Transactional
    public Pair<Boolean, String> updateOrderStatus(String orderNo, Integer status) {
        String key = "lock:term:order:status:" + orderNo;
        Pair result = lockOnRedisManager.handleByUnfair(key, 3, 3, Pair.class, () -> {
            Order order = getOrderByOrderNo(orderNo);
            if (order == null) {
                return new Pair<>(false, "订单不存在");
            } else if (!Integer.valueOf(0).equals(order.getIStatus())) {
                return new Pair<>(false, "订单当前状态无法变更");
            } else if (order.getIStatus().equals(status)) {
                return new Pair<>(false, "订单当前状态无法变更");
            }

            Order temp1 = new Order();
            temp1.setId(order.getId());
            temp1.setIStatus(status);
            tOrderDAO.updateByPrimaryKeySelective(temp1);

            return new Pair<>(true, null);
        });
        if (result == null)
            return new Pair<>(false, "服务繁忙，请稍后访问");

        return result;
    }

    public Order getOrderByOrderNo(String orderNo) {
        Order temp = new Order();
        temp.setSOrderNo(orderNo);
        return tOrderDAO.selectOne(temp);
    }
}
