package com.zhichanzaixian.trademarkapi;

import com.zhichanzaixian.trademarkapi.api.LoginClient;
import com.zhichanzaixian.trademarkapi.api.ReactorClient;
import com.zhichanzaixian.trademarkapi.api.request.LoginRequest;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by syl nerosyl@live.com on 2022/9/21
 * <p>
 * [make sure redis is running]:
 * <p>
 * docker run --name test-redis-login -p 0.0.0.0:6379:6379 -d redis
 *
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SessionTest {


    //http-client
    @Autowired
    LoginClient loginClient;

    //http-client
    @Autowired
    ReactorClient reactorClient;


    @PostConstruct
    public void wareUp() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> userLoginThenAccessReactorProject("ware-Up-"+ finalI)).start();
        }
        TimeUnit.SECONDS.sleep(20);

    }


    /**
     * <p>
     * [make sure redis is running]:
     * <p>
     * docker run --name test-redis-login -p 0.0.0.0:6379:6379 -d redis
     * <p>
     * ----This may need to be run multiple times. Seems to be a concurrency problem.
     *
     */
    @RepeatedTest(3)
    void mainTest() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> userLoginThenAccessReactorProject("user"+ finalI)).start();
        }

        //spring.session.timeout is 20s ,  wait for SessionDestroyedEvent error log : Unable to publish SessionDestroyedEvent for session f87...
        TimeUnit.SECONDS.sleep(32);
    }



    void userLoginThenAccessReactorProject(String userName) {



        log.info("========>[{}],try to login... ",userName);

        String XAuthToken = login(userName);

       // TimeUnit.SECONDS.sleep(1);

        for (int i = 0; i < 10; i++) {
            new Thread(new AccessReactorProjectRunnable( XAuthToken)).start();
        }

        log.info("=======>[{}],access RESTFUL api  in reactor project  immediately",userName);



    }

    private String login(String userName) {
        LoginRequest request = new LoginRequest();
        request.setUsername(userName);
        request.setPassword("1234");
        Response response = loginClient.login(request);
        Map<String, Collection<String>> headers = response.headers();
        Collection<String> strs = headers.get("X-Auth-Token");
        return strs.iterator().next();
    }




    private class AccessReactorProjectRunnable implements Runnable {
   //     private CyclicBarrier barrier;
        private String token;

        public AccessReactorProjectRunnable(String token) {
            this.token = token;
        }


        @Override
        public void run() {
            try {
                //access api from reactor project
                Response login = reactorClient.userProfile(token);
            }catch (Exception e){
                log.error("Error ! reactor project is not run ? {}",e.getMessage());
            }

        }
    }
}




