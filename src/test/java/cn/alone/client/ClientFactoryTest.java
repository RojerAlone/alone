package cn.alone.client;

import org.junit.jupiter.api.Test;

/**
 * Created by rojeralone on 2018-07-27
 */
class ClientFactoryTest {

    interface UserInterface {
        String sayHello();
    }

    @Test
    void proxy() {
        UserInterface user = ClientFactory.getProxy(UserInterface.class);
        Object obj = user.sayHello();
        System.out.println(obj);
    }
}