package com.zzqfsy.order.rpc;

import com.zzqfsy.api.resp.BaseResp;
import com.zzqfsy.api.rpc.IOrderFacade;
import com.zzqfsy.order.domain.Order;
import com.zzqfsy.order.repository.OrderRepository;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 12:48 2018/8/21
 * @Modified By:
 **/
@RestController
@RequestMapping(value = "/order")
public class OrderFacade implements IOrderFacade {

    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @Override
    public BaseResp createOrder(String projectId, String userId, String amount) {
        Integer iProjectId ;
        Integer iUserId;
        BigDecimal dcmAmount;
        if (StringUtils.isEmpty(projectId) || (iProjectId = Integer.valueOf(projectId)) <= 0 ||
                StringUtils.isEmpty(userId) || (iUserId = Integer.valueOf(userId)) <= 0 ||
                StringUtils.isEmpty(amount) ||
                (dcmAmount = new BigDecimal(amount)) ==  null ||
                BigDecimal.ZERO.compareTo(dcmAmount) == 0){
            return BaseResp.getFailInstance("参数错误");
        }

        Order order = orderRepository.createOrder(iProjectId, iUserId, dcmAmount);

        return BaseResp.getInstance(order.getSOrderNo());
    }

    @RequestMapping(value = "/{orderNo}", method = RequestMethod.PUT)
    @Override
    public BaseResp updateOrderStatus(@PathVariable("orderNo") String orderNo, @RequestParam("orderStatus") String orderStatus) {
        if (StringUtils.isEmpty(orderNo) ||
                (!"completed".equals(orderStatus) && !"canceled".equals(orderStatus))){
            return BaseResp.getFailInstance("参数错误");
        }

        Pair<Boolean, String> result = orderRepository.updateOrderStatus(orderNo, ("completed".equals(orderStatus) ? 1 : 2));
        if (!result.getKey()){
            return BaseResp.getFailInstance(result.getValue());
        }
        return BaseResp.getInstance();
    }


    @RequestMapping(value = "/{orderNo}", method = RequestMethod.GET)
    @Override
    public BaseResp getOrderByOrderNo(@PathVariable("orderNo") String orderNo) {
        if (StringUtils.isEmpty(orderNo)){
            return BaseResp.getFailInstance("参数错误");
        }
        return BaseResp.getInstance(orderRepository.getOrderByOrderNo(orderNo));
    }
}
