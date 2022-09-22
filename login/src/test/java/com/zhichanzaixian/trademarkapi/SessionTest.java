package com.zhichanzaixian.trademarkapi;

import com.zhichanzaixian.trademarkapi.api.LoginClient;
import com.zhichanzaixian.trademarkapi.api.ReactorClient;
import com.zhichanzaixian.trademarkapi.api.request.LoginRequest;
import feign.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by syl nerosyl@live.com on 2022/9/21
 *
 * @author syl
 */
@Slf4j
@SpringBootTest
public class SessionTest {

    @Autowired
    LoginClient loginClient;

    @Autowired
    ReactorClient redactorClient;


    @PostConstruct
    public void wareUp() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> userLogin("ware-Up-"+ finalI)).start();
        }
        TimeUnit.SECONDS.sleep(20);

    }


    @Test
    void mainTest() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> userLogin("user"+ finalI)).start();
        }

        TimeUnit.SECONDS.sleep(30);
    }



    void userLogin(String userName) {



        log.info("========>[{}],try to login... ",userName);

        String XAuthToken = login(userName);

       // TimeUnit.SECONDS.sleep(1);

        for (int i = 0; i < 10; i++) {
            new Thread(new MyRunnable( XAuthToken)).start();
        }

        log.info("=======>[{}],access RESTFUL api  in reactor project  immediately ========",userName);



    }

    private String login(String userName) {
        LoginRequest request = new LoginRequest();
        request.setUsername(userName);
        request.setPassword("1234");
        Response response = loginClient.login(request);
        Map<String, Collection<String>> headers = response.headers();
        Collection<String> strs = headers.get("X-Auth-Token");
        String token = strs.iterator().next();
        return token;
    }




    private class MyRunnable implements Runnable {
   //     private CyclicBarrier barrier;
        private String token;

        public MyRunnable(String token) {
            this.token = token;
        }

        @SneakyThrows
        @Override
        public void run() {
            Response login = redactorClient.userProfile(token);
        }
    }
}




