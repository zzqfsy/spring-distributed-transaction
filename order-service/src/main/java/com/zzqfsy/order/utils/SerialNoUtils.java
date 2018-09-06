package com.zzqfsy.order.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SerialNoUtils {
    private static final Random random = new Random();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMdd");
    private static final int max=999;
    private static final int min=100;
    private static final AtomicInteger poolNumber = new AtomicInteger(0);

    /**
     * 生成32位序列号
     */
    public static String generatorSerialNo(SerialNoPrefixEnum prefixName, String userId){
        StringBuilder sb = new StringBuilder();
        return sb.append(prefixName.getValue())
                .append(generatorNightString(userId))
                .append(sdf.format(new Date()))
                .append(random.nextInt(max)%(max-min+1) + min)
                .append(poolNumber.incrementAndGet() % 10)
                .toString();
    }

    private static String generatorNightString(String value){
        while (value.length() < 9){
            value = "0" + value;
        }
        return value;
    }
}
