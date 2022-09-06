package com.xrb.c7;

import com.xrb.c7.servcie.MyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/** ajc编译器
 * @author xieren8iao
 * @date 2022/9/5 20:02
 */
@SpringBootApplication
public class C7Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(C7Application.class, args);
        MyService myService = context.getBean(MyService.class);

//        MyService myService = new MyService();
        myService.foo();

//        context.close();
    }
}