package com.zzqfsy.api.decoder;

import com.alibaba.fastjson.JSONObject;
import feign.Response;
import feign.codec.StringDecoder;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 21:59 2018/8/19
 * @Modified By:
 **/
public class FeignClassDecoder extends StringDecoder {

    @Override
    public Object decode(Response response, Type type) throws IOException {
        Object object = super.decode(response, String.class);
        if (String.class != type){
            object = JSONObject.parseObject((String)object, type);
        }
        return object;
    }
}
