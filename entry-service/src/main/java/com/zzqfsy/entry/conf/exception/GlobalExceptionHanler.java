package com.zzqfsy.entry.conf.exception;

import com.zzqfsy.api.resp.BaseResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zzqana on 8/15/2016.
 */
@ControllerAdvice
public class GlobalExceptionHanler {
    private static Logger logger= LoggerFactory.getLogger(GlobalExceptionHanler.class);

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResp errorHandler1(HttpServletRequest req, HttpRequestMethodNotSupportedException exception) throws Exception {
        loggerException(exception, true);
        return BaseResp.getFailInstance("请求方法不支持");
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResp errorHandler(HttpServletRequest req, NoHandlerFoundException exception) throws Exception {
        loggerException(exception, false);
        return BaseResp.getFailInstance("请求路径不存在");
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResp errorHandler(HttpServletRequest req, Exception exception) throws Exception {
        loggerException(exception, true);
        return BaseResp.getFailInstance("服务器繁忙，请稍后重试");
    }

    private void loggerException(Exception ex, boolean isSendEmail){
        logger.error(ex.getMessage());
        StringBuilder traceSB = new StringBuilder();
        for(StackTraceElement elem : ex.getStackTrace()) {
            traceSB.append("at " + elem + "\r\n");
        }
        logger.error(traceSB.toString());

    }
}
