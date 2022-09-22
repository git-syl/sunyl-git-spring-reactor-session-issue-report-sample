package com.zhichanzaixian.trademarkapi.exception;


import com.zhichanzaixian.trademarkapi.comm.result.CodeMsg;

/**
 * @author syl  Date: 2018/7/1 Email:nerosyl@live.com
 */
public class GlobalException extends RuntimeException {
    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public void setCm(CodeMsg cm) {
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
