package com.zhichanzaixian.trademarkapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhichanzaixian.trademarkapi.comm.result.CodeMsg;
import com.zhichanzaixian.trademarkapi.comm.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author syl  Date: 2019/11/12 Email:nerosyl@live.com
 */
@Component
public class CustomExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        HttpServletResponse response = event.getResponse();
        response.setContentType("application/json");
        //response.setStatus(401);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Result.error(CodeMsg.PARAM_ERROR_EMPTY.findArgsObject("多处登录已超过上线！须等待其他用户退出登录。 若不是您本人登录 请尽快修改登录密码。ss"))));
        response.getWriter().flush();
    }
}
