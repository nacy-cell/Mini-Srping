package test.org.springframework.ioc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import test.org.springframework.service.HelloService;

import static org.assertj.core.api.Assertions.assertThat;

public class AwareInterfaceTest {

    @Test
    public void test() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        HelloService helloService = applicationContext.getBean("helloService", HelloService.class);

        assertThat(helloService.getBeanFactory().equals(applicationContext.getBeanFactory())).isTrue();
        assertThat(helloService.getApplicationContext().equals(applicationContext)).isTrue();

        assertThat(helloService.getApplicationContext()).isNotNull();
        assertThat(helloService.getBeanFactory()).isNotNull();


    }
}
