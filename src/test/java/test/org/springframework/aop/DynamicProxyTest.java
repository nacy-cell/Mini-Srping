package test.org.springframework.aop;

import org.junit.Test;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.TargetSource;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.JdkDynamicAopProxy;
import test.org.springframework.common.WorldServiceInterceptor;
import test.org.springframework.service.WorldService;
import test.org.springframework.service.WorldServiceImpl;

public class DynamicProxyTest {

    @Test
    public void testJdkDynamicProxy() throws Exception {
        WorldService worldService = new WorldServiceImpl();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        TargetSource targetSource = new TargetSource(worldService);
        WorldServiceInterceptor methodInterceptor = new WorldServiceInterceptor();

        MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* test.org.springframework.service.WorldService.explode(..))").getMethodMatcher();

        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodInterceptor(methodInterceptor);
        advisedSupport.setMethodMatcher(methodMatcher);

        WorldService proxy = (WorldService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        proxy.explode();

    }
}
