package com.xrb.c11;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * @author xieren8iao
 * @date 2022/9/6 22:17
 */
public class C11 {
    public static void main(String[] args) throws NoSuchMethodException {
        StaticMethodMatcherPointcut pointcut=new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                MergedAnnotations annotations = MergedAnnotations.from(method);
                if (annotations.isPresent(Transactional.class)){
                    return true;
                }
                return false;
            }
        };
        //寻找策略不同 按照层级树查找
        StaticMethodMatcherPointcut pointcut2=new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                MergedAnnotations annotations = MergedAnnotations.from(method, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY);
                if (annotations.isPresent(Transactional.class)){
                    return true;
                }
                return false;
            }
        };

        System.out.println(pointcut.matches(T3.class.getMethod("foo"), T3.class));
        System.out.println(pointcut.matches(T2.class.getMethod("foo"), T2.class));
    }
    @Transactional
    static interface T1{
        void foo();
    }

     class T2 implements T1{

         @Override
         public void foo() {
             System.out.println("foo");
         }
     }
     class T3{
        @Transactional
        public void foo(){
            System.out.println("T3 foo");
        }
     }
}