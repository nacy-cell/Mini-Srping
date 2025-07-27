package test.org.springframework.bean.factory;

import java.time.LocalDateTime;

import java.time.LocalDateTime;
import java.lang.reflect.Method;

public class BeanClassTest {
    public static void main(String[] args) throws Exception {
        Class<LocalDateTime> clazz = LocalDateTime.class;
        // 获取now()静态方法（无参数）
        Method nowMethod = clazz.getDeclaredMethod("now");
        nowMethod.getName();
        // 打印方法的返回值类型
        System.out.println(        nowMethod.getName()+nowMethod.getReturnType()); // 输出：class java.time.LocalDateTime
    }
}