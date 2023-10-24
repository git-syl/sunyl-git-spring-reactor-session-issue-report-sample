package com.zhichanzaixian.trademarkapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * <p>
 * [make sure redis is running]:
 * <p>
 * docker run --name test-redis-login -p 0.0.0.0:6379:6379 -d redis
 */
@EnableWebSecurity
@EnableFeignClients
@SpringBootApplication
@EnableCaching
public class LoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class, args);
    }

    static {
        //Please note that both projects need to connect to the same Redis container,
        // and it is recommended to test with an external Redis container.
        if (false) {
            GenericContainer<?> redis =
                    new GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine"))
                            .withExposedPorts(6379);
            redis.start();
            System.err.println("[redis-host]>>>>" + redis.getHost());
            System.err.println("[redis-port]>>>>" + redis.getMappedPort(6379).toString());

            System.setProperty("spring.redis.host", redis.getHost());
            System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
        }
    }

}
