package com.xrb.c5;

import com.xrb.c5.component.Config;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author xieren8iao
 * @date 2022/9/1 22:11
 */
public class ComponentScanPostProcessor implements BeanFactoryPostProcessor {
    //手写模拟扫描bean
    /**
     * 1.先拿到ComponentScan注解，找出扫描的路径
     * 2.根据通配符写法拿到class二进制资源
     * 3.分析class资源 是否有对应注解
     * 4.根据类信息、注解信息定义bean定义
     * 5.加进bean工厂
     */
    /**
     * Modify the application context's internal bean factory after its standard
     * initialization. All bean definitions will have been loaded, but no beans
     * will have been instantiated yet. This allows for overriding or adding
     * properties even to eager-initializing beans.
     *
     * @param beanFactory the bean factory used by the application context
     * @throws BeansException in case of errors
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            ComponentScan componentScan = AnnotationUtils.findAnnotation(Config.class, ComponentScan.class);
            if (componentScan != null) {
                String[] basePackages = componentScan.basePackages();
                for (String basePackage : basePackages) {
                    System.out.println("basePackage = " + basePackage);
                    //com.xrb.c5.component ==> classpath*:com/xrb/c5/component/**/*.class
                    String path = "classpath*:" + basePackage.replace(".", "/") + "/*.class";
                    CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();
                    Resource[] resources = new PathMatchingResourcePatternResolver().getResources(path);
                    AnnotationBeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

                    for (Resource resource : resources) {
                        MetadataReader reader = factory.getMetadataReader(resource);
                        System.out.println("类名:" + reader.getClassMetadata().getClassName());
                        AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
                        String componentClassName = Component.class.getName();
                        System.out.println("是否含有@Component 派生注解:" + annotationMetadata.hasMetaAnnotation(componentClassName));
                        if (annotationMetadata.hasAnnotation(componentClassName) || annotationMetadata.hasMetaAnnotation(componentClassName)) {
                            AbstractBeanDefinition bd = BeanDefinitionBuilder.genericBeanDefinition(reader.getClassMetadata().getClassName())
                                    .getBeanDefinition();
                            //创建bean定义
                            if (beanFactory instanceof DefaultListableBeanFactory) {
                                String beanName = beanNameGenerator.generateBeanName(bd, (DefaultListableBeanFactory)beanFactory);
                                //注册bean定义
                                ((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(beanName, bd);
                            }

                        }
                    }
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}