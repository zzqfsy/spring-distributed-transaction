package com.zzqfsy.order.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 12:42 2018/8/21
 * @Modified By:
 **/
@Getter
@Setter
@Table(name = "t_order")
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer iProjectId;
    private Integer iUserId;
    private String sOrderNo;
    private BigDecimal dcmAmount;
    private Integer iStatus;
}
