package cn.tedu.sxwork;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootTest
class SXworkApplicationTests {

    @Test
    void contextLoads() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println("localhost: " + localHost);
        System.out.println("getHostAddress:  " + localHost.getHostAddress());
        System.out.println("getHostName:  " + localHost.getHostName());
    }

}
