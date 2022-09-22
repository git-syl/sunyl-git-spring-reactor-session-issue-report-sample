# spring-reactor-session-issue-report

#### introduce
    spring-reactor-session-issue-report-sample
    JDK17 + SpringBoot 2.6.11



#### To Reproduce
Install Redis and start, port 6379. no password.

1、Import  Maven project into the IDE.

2、Run the login project.(LoginApplication.java )

3、Run the reactor-project-gateway.(GatewayApplication.java)

finally ,Run junit-test in login project (SessionTest.java) code like :


```
@Test
void test(){
//STEP-01 : Login and get token, in login project  , it is  Spring Boot applicaiton (Servlet)
 String XAuthToken = login(userName);

//STEP-02 : Access RESTFUL api  in reactor project  immediately:
    for (int i = 0; i < 10; i++) {
            new Thread(new MyRunnable( XAuthToken)).start();
        }
}
```
---- You can see the WARN :"2022-09-22 17:11:33.171 WARN 23620 --- [nerContainer-49] o.s.s.d.r.RedisIndexedSessionRepository : Unable to publish SessionDestroyedEvent for session 727ae15d-7f42-4f8d-b229-f8385ac54b06", And Redis won't expire the key.

----This may need to be run multiple times. Seems to be a concurrency problem.