package com.zzqfsy.account.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 8:24 2018/8/17
 * @Modified By:
 **/
@Getter
@Setter
@Table(name = "t_account_flow")
public class AccountFlow {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer iUserId;
    private BigDecimal dcmBalanceBefore;
    private BigDecimal dcmBalanceChange;
    private BigDecimal dcmBalanceAfter;
    private Integer iStatus;
    private String sOrderNo;
}
