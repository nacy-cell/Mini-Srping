package org.springframework.aop.framework;
import org.aopalliance.intercept.MethodInterceptor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkDynamicAopProxy implements  AopProxy, InvocationHandler {

    private AdvisedSupport advised;

    public  JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(getClass().getClassLoader(),
                advised.getTargetSource().getTargetClass(), this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            判断当前方法是否需要被代理（matches）
//            如果需要代理，创建方法调用对象并使用拦截器处理
//            如果不需要代理，直接反射调用原始方法
        if (advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass())) {
            MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
            return methodInterceptor.invoke(
                    new ReflectiveMethodInvocation(advised.getTargetSource().getTarget(), method, args)
            );
        }
        return method.invoke(advised.getTargetSource().getTarget(), args);
    }
}
