package com.zhichanzaixian.trademarkapi.comm.result;

/**
 * @author syl  Date: 2018/6/25 Email:nerosyl@live.com
 */
public class CodeMsg {
    private int code;
    private String msg;

    public static CodeMsg SUCCESS = new CodeMsg(0, "success");

    //1000
    public static CodeMsg SINGLE_ERROR = new CodeMsg(1000, "签名错误:%s");

    //20000 - 远程调用错误
    public static CodeMsg REMOTE_CALL_ERROR = new CodeMsg(2002, "远程调用错误原因:%s");

    //3000 - 服务端错误
    public static CodeMsg SERVER_ERROR_MSG = new CodeMsg(3002, "操作失败原因:%s");



    //4000 - 客户端错误
    public static CodeMsg BIND_ERROR = new CodeMsg(4001, "输入有误:%s");
    public static CodeMsg PARAM_ERROR = new CodeMsg(4002, "传入参数有误:%s");
    public static CodeMsg FORM_REPEAT = new CodeMsg(4005, "%s");

    public static CodeMsg PARAM_ERROR_EMPTY = new CodeMsg(4007, "%s");
    public static CodeMsg ACCOUNT_LIMIT = new CodeMsg(4006, "账号被限制: %s");
    public static CodeMsg ACCESS_DECLINE = new CodeMsg(4005, "访问被拒绝。%s");
    public static CodeMsg NOT_LOGIN = new CodeMsg(4004, "登录状态失效，请重新登录。s");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(4003, "%s");


    //50001 - 业务相关错误。
    public static CodeMsg CUSTOMER_LEVEL_LMIT = new CodeMsg(5001, "您的账号暂时无法添加客户。");
    public static CodeMsg EMPTY_PAY_PASSWORD = new CodeMsg(5002, "支付密码为空，请先设置支付密码。");

    public static CodeMsg WECHAT_PAY = new CodeMsg(6001, "微信错误。:%s");
    public static CodeMsg JS_CODE_ERROR = new CodeMsg(6002, "%s");




    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg findArgsObject(Object... args) {
        return new CodeMsg(this.code, String.format(this.msg, args));
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
