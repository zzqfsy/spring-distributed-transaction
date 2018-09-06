package com.zzqfsy.order.utils;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 13:54 2018/8/21
 * @Modified By:
 **/
public enum SerialNoPrefixEnum {
    PROJECT_ORDER("PO","产品订单"),
    ;

    private String value;
    private String desc;
    SerialNoPrefixEnum(String value, String desc){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
