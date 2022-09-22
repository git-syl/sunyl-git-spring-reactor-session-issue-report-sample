package com.zhichanzaixian.trademarkapi.exception;


import com.zhichanzaixian.trademarkapi.comm.result.CodeMsg;
import com.zhichanzaixian.trademarkapi.comm.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * @author syl  Date: 2018/7/1 Email:nerosyl@live.com
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = GlobalException.class)
    public Result<String> globalExceptionHandler(HttpServletRequest request, Exception e) {
        log.error("GlobalExceptionHandler:", e);
        GlobalException ex = (GlobalException) e;
        return Result.error(ex.getCm());
    }


    @ExceptionHandler(value = BindException.class)
    public Result<String> bindException(HttpServletRequest request, Exception e) {
        log.error("BindException:", e);
        BindException ex = (BindException) e;
        String message = "";
        if (ex.getBindingResult() != null && ex.getBindingResult().getFieldError() != null) {
            String field = ex.getBindingResult().getFieldError().getField();
            Object rejectedValue = ex.getBindingResult().getFieldError().getRejectedValue();
            message = ex.getAllErrors().get(0).getDefaultMessage() + "（ at:" + field + "，rejectValue:" + rejectedValue + "）";
        }

        return Result.error(CodeMsg.BIND_ERROR.findArgsObject(message));
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<String> methodArgumentNotValidException(Exception e) {
        log.error("MethodArgumentNotValidException:", e);
        MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
        String message = "";
        if (ex.getBindingResult() != null && ex.getBindingResult().getFieldError() != null) {
            String field = ex.getBindingResult().getFieldError().getField();
            Object rejectedValue = ex.getBindingResult().getFieldError().getRejectedValue();
            message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage() + "（ at:" + field + "，rejectValue:" + rejectedValue + "）";
        }
        return Result.error(CodeMsg.BIND_ERROR.findArgsObject(message));
    }




    @ExceptionHandler(value = NullPointerException.class)
    public Result<String> nullPointerException(Exception e) {
        log.error("NullPointerException:", e);
        return Result.error(CodeMsg.SERVER_ERROR_MSG.findArgsObject(e.getMessage()));
    }



}
