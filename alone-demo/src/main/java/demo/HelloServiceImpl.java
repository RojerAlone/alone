package demo;

/**
 * @author RojerAlone
 * @Date 2018-08-11 13:10
 */
public class HelloServiceImpl implements HelloService {
    public String sayHello() {
        String res = "Hello world";
        System.out.println(res);
        return res;
    }
}
