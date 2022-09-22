package com.zhichanzaixian.trademarkapi.hanlder;


import com.zhichanzaixian.trademarkapi.result.BaseResult;
import com.zhichanzaixian.trademarkapi.result.ResultOld;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Map;
@Slf4j
public class GlobalExceptionHandler extends DefaultErrorWebExceptionHandler {



    public GlobalExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    public Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

        Throwable error = getError(request);

        if (error  instanceof ResponseStatusException){
            log.error("ResponseStatusException-{}",error.getMessage());
        }else {
            log.error("renderErrorResponse",error);
        }


        Map<String, Object> errorPropertiesMap = getErrorAttributes(request, ErrorAttributeOptions.defaults());



        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(ResultOld.error(400,BaseResult.SYSTEM_ERROR, error.getMessage(),errorPropertiesMap)));
    }
}