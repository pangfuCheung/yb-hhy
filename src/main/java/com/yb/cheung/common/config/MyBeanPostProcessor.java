package com.yb.cheung.common.config;

import com.yb.cheung.common.annotation.YBRequestParamResolver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 注册自定义注解使用@Companent不生效
 */
@Configuration
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private YBRequestParamResolver YBRequestParamResolver;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //System.out.println("-------------------------------"+beanName);
        if(beanName.equals("requestMappingHandlerAdapter")){
            //requestMappingHandlerAdapter进行修改
            RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter) bean;
            List<HandlerMethodArgumentResolver> argumentResolvers = adapter.getArgumentResolvers();
            //添加自定义参数处理器
            argumentResolvers = addArgumentResolvers(argumentResolvers);
            adapter.setArgumentResolvers(argumentResolvers);
        }
        return bean;
    }

    private List<HandlerMethodArgumentResolver> addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
        //将自定的添加到最前面
        resolvers.add(YBRequestParamResolver);
        //将原本的添加后面
        resolvers.addAll(argumentResolvers);
        return  resolvers;
    }

}
