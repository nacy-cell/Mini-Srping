package org.springframework.aop;

import org.springframework.aop.ClassFilter;

public interface Pointcut {

    /**
     * 判断切入点应该应用到哪些类上
     */
    ClassFilter getClassFilter();

    /**
     * 判断切入点应该应用到哪些方法上
     */
    MethodMatcher getMethodMatcher();
}
