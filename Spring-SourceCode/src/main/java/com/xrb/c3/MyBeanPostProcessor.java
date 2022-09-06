package com.xrb.c3;

import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.logging.Logger;

/**
 * @author xieren8iao
 * @date 2022/8/31 21:00
 */
@Component
public class MyBeanPostProcessor implements InstantiationAwareBeanPostProcessor, DestructionAwareBeanPostProcessor {
    /**
     * Apply this BeanPostProcessor to the given bean instance before its
     * destruction, e.g. invoking custom destruction callbacks.
     * <p>Like DisposableBean's {@code destroy} and a custom destroy method, this
     * callback will only apply to beans which the container fully manages the
     * lifecycle for. This is usually the case for singletons and scoped beans.
     *
     * @param bean     the bean instance to be destroyed
     * @param beanName the name of the bean
     * @throws BeansException in case of errors
     * @see DisposableBean#destroy()
     * @see AbstractBeanDefinition#setDestroyMethodName(String)
     */
    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if (isLifeCircleBean(beanName)){
            System.out.println("postProcessBeforeDestruction<<<<<<销毁之前执行，如@PreDestroy");
        }
    }

    /**
     * Determine whether the given bean instance requires destruction by this
     * post-processor.
     * <p>The default implementation returns {@code true}. If a pre-5 implementation
     * of {@code DestructionAwareBeanPostProcessor} does not provide a concrete
     * implementation of this method, Spring silently assumes {@code true} as well.
     *
     * @param bean the bean instance to check
     * @return {@code true} if {@link #postProcessBeforeDestruction} is supposed to
     * be called for this bean instance eventually, or {@code false} if not needed
     * @since 4.3
     */
    @Override
    public boolean requiresDestruction(Object bean) {
        return false;
    }

    /**
     * Apply this BeanPostProcessor <i>before the target bean gets instantiated</i>.
     * The returned bean object may be a proxy to use instead of the target bean,
     * effectively suppressing default instantiation of the target bean.
     * <p>If a non-null object is returned by this method, the bean creation process
     * will be short-circuited. The only further processing applied is the
     * {@link #postProcessAfterInitialization} callback from the configured
     * {@link BeanPostProcessor BeanPostProcessors}.
     * <p>This callback will be applied to bean definitions with their bean class,
     * as well as to factory-method definitions in which case the returned bean type
     * will be passed in here.
     * <p>Post-processors may implement the extended
     * {@link SmartInstantiationAwareBeanPostProcessor} interface in order
     * to predict the type of the bean object that they are going to return here.
     * <p>The default implementation returns {@code null}.
     *
     * @param beanClass the class of the bean to be instantiated
     * @param beanName  the name of the bean
     * @return the bean object to expose instead of a default instance of the target bean,
     * or {@code null} to proceed with default instantiation
     * @throws BeansException in case of errors
     * @see #postProcessAfterInstantiation
     * @see AbstractBeanDefinition#getBeanClass()
     * @see AbstractBeanDefinition#getFactoryMethodName()
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (isLifeCircleBean(beanName)){
            System.out.println("postProcessBeforeInstantiation<<<<<< 实例化之前执行，这里返回的对象会替换原本的bean");
        }
        return null;
    }

    private boolean isLifeCircleBean(String beanName) {
        return beanName.equals("lifeCircleBean");
    }

    /**
     * Perform operations after the bean has been instantiated, via a constructor or factory method,
     * but before Spring property population (from explicit properties or autowiring) occurs.
     * <p>This is the ideal callback for performing custom field injection on the given bean
     * instance, right before Spring's autowiring kicks in.
     * <p>The default implementation returns {@code true}.
     *
     * @param bean     the bean instance created, with properties not having been set yet
     * @param beanName the name of the bean
     * @return {@code true} if properties should be set on the bean; {@code false}
     * if property population should be skipped. Normal implementations should return {@code true}.
     * Returning {@code false} will also prevent any subsequent InstantiationAwareBeanPostProcessor
     * instances being invoked on this bean instance.
     * @throws BeansException in case of errors
     * @see #postProcessBeforeInstantiation
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (isLifeCircleBean(beanName)){
            System.out.println("postProcessAfterInstantiation<<<<< 实例化之后执行，这里如果返回false会跳过依赖注入阶段");
        }
//        return false;
        return true;
    }

    /**
     * Post-process the given property values before the factory applies them
     * to the given bean, without any need for property descriptors.
     * <p>Implementations should return {@code null} (the default) if they provide a custom
     * {@link #postProcessPropertyValues} implementation, and {@code pvs} otherwise.
     * In a future version of this interface (with {@link #postProcessPropertyValues} removed),
     * the default implementation will return the given {@code pvs} as-is directly.
     *
     * @param pvs      the property values that the factory is about to apply (never {@code null})
     * @param bean     the bean instance created, but whose properties have not yet been set
     * @param beanName the name of the bean
     * @return the actual property values to apply to the given bean (can be the passed-in
     * PropertyValues instance), or {@code null} which proceeds with the existing properties
     * but specifically continues with a call to {@link #postProcessPropertyValues}
     * (requiring initialized {@code PropertyDescriptor}s for the current bean class)
     * @throws BeansException in case of errors
     * @see #postProcessPropertyValues
     * @since 5.1
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (isLifeCircleBean(beanName)){
            System.out.println("postProcessProperties<<<<<< 依赖注入阶段执行，如@Autowired,@Value,@Resource");
        }
        return pvs;
    }



    /**
     * Apply this {@code BeanPostProcessor} to the given new bean instance <i>before</i> any bean
     * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
     * or a custom init-method). The bean will already be populated with property values.
     * The returned bean instance may be a wrapper around the original.
     * <p>The default implementation returns the given {@code bean} as-is.
     *
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     * @return the bean instance to use, either the original or a wrapped one;
     * if {@code null}, no subsequent BeanPostProcessors will be invoked
     * @throws BeansException in case of errors
     * @see InitializingBean#afterPropertiesSet
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (isLifeCircleBean(beanName)){
            System.out.println("postProcessBeforeInitialization<<<< 初始化之前执行，这里返回的对象会替换原本的bean，如@PostConstruct @ConfigurationProperties");
        }
        return bean;
    }

    /**
     * Apply this {@code BeanPostProcessor} to the given new bean instance <i>after</i> any bean
     * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
     * or a custom init-method). The bean will already be populated with property values.
     * The returned bean instance may be a wrapper around the original.
     * <p>In case of a FactoryBean, this callback will be invoked for both the FactoryBean
     * instance and the objects created by the FactoryBean (as of Spring 2.0). The
     * post-processor can decide whether to apply to either the FactoryBean or created
     * objects or both through corresponding {@code bean instanceof FactoryBean} checks.
     * <p>This callback will also be invoked after a short-circuiting triggered by a
     * {@link InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation} method,
     * in contrast to all other {@code BeanPostProcessor} callbacks.
     * <p>The default implementation returns the given {@code bean} as-is.
     *
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     * @return the bean instance to use, either the original or a wrapped one;
     * if {@code null}, no subsequent BeanPostProcessors will be invoked
     * @throws BeansException in case of errors
     * @see InitializingBean#afterPropertiesSet
     * @see FactoryBean
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (isLifeCircleBean(beanName)){
            System.out.println("postProcessAfterInitialization<<<<<<初始化之后执行，致力返回的对象会替换原本的bean，如代理增强");
        }
        return bean;
    }
}