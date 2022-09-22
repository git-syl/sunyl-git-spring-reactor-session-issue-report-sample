/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhichanzaixian.trademarkapi.module;

import com.zhichanzaixian.trademarkapi.result.ResultOld;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.CIRCUITBREAKER_EXECUTION_EXCEPTION_ATTR;

/**
 * @author
 */
@Slf4j
@RestController
@RequestMapping("/gatewayFallback")
public class FallBackController {


    @RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseEntity<ResultOld<Object>> getFallback(ServerWebExchange exchange)   {
        Throwable cause = exchange.getAttribute(CIRCUITBREAKER_EXECUTION_EXCEPTION_ATTR);
        Throwable rootCause = ExceptionUtils.getRootCause(cause);
        log.error("rootCause : ", rootCause);
        log.error("Failure, cause was : ", cause);

        String defaultStr = "message";

        if (rootCause == null && cause instanceof java.util.concurrent.TimeoutException) {
            // Gateway Timeout
            return new ResponseEntity<>(ResultOld.error(-1, defaultStr, "gatewayFallback:" + cause.getMessage()), HttpStatus.OK);

        } else {
            //other error
            if (cause == null) {
                return new ResponseEntity<>(ResultOld.error(-1, defaultStr, "gatewayFallback:no Throwable"), HttpStatus.REQUEST_TIMEOUT);
            } else {
                return new ResponseEntity<>(ResultOld.error(-1, defaultStr, "gatewayFallback:" + cause.getMessage()), HttpStatus.OK);
            }

        }
    }


}
