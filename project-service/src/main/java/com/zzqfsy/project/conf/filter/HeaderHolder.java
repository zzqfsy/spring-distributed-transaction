package com.zzqfsy.project.conf.filter;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 12:17 2018/8/21
 * @Modified By:
 **/
public class HeaderHolder {

    private static ThreadLocal<Integer> serialNumber = new ThreadLocal<>();

    public static void setSerialNumber(Integer value){
        serialNumber.set(value);
    }

    public static Integer getSerialNumber(){
        return serialNumber.get();
    }
}
