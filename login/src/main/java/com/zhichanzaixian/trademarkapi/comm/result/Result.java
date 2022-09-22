package com.zhichanzaixian.trademarkapi.comm.result;

//import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: syl  Date: 2018/6/25 Email:nerosyl@live.com
 */
@Data
@JsonView(Result.ResultView.class)
public class Result<T>  implements Serializable {



    private int code;



    private String message;


    private T data;

    private Result(T data) {
        this.code = 0;
        this.message = "success";
        this.data = data;
    }

    private Result(CodeMsg codeMsg) {
        if (codeMsg == null) {
            return;
        }
        this.code = codeMsg.getCode();
        this.message = codeMsg.getMsg();
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    public static <T> Result<T> error(CodeMsg codeMsg) {
        if (codeMsg == null) {
            codeMsg = CodeMsg.PARAM_ERROR;
        }
        return new Result<T>(codeMsg);
    }


    public interface ResultView {

    }
}
