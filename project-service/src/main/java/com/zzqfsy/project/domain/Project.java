package com.zzqfsy.project.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 10:31 2018/8/21
 * @Modified By:
 **/
@Getter
@Setter
@Table(name = "t_project")
public class Project {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String sTitle;
    private BigDecimal dcmTotal;
    private BigDecimal dcmAble;
}
