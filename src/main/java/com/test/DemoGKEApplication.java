package com.test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class DemoGKEApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DemoGKEApplication.class, args);
	}

	@Bean
    public Docket api() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				//.ignoredParameterTypes(Pageable.class)
				.select()
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any())
//				.paths(Predicates.not(PathSelectors.regex("/actuator.*")))
//				.paths(Predicates.not(PathSelectors.regex("/error.*")))
//				.paths(Predicates.not(PathSelectors.regex("/health.*")))
//				.paths(Predicates.not(PathSelectors.regex("/trace.*")))
//				.paths(Predicates.not(PathSelectors.regex("/resume.*")))
//				.paths(Predicates.not(PathSelectors.regex("/restart.*")))
//				.paths(Predicates.not(PathSelectors.regex("/refresh.*")))
//				.paths(Predicates.not(PathSelectors.regex("/pause.*")))
//				.paths(Predicates.not(PathSelectors.regex("/archaius.*")))
//				.paths(Predicates.not(PathSelectors.regex("/autoconfig.*")))
//				.paths(Predicates.not(PathSelectors.regex("/beans.*")))
//				.paths(Predicates.not(PathSelectors.regex("/configprops.*")))
//				.paths(Predicates.not(PathSelectors.regex("/dump.*")))
//				.paths(Predicates.not(PathSelectors.regex("/env.*")))
//				.paths(Predicates.not(PathSelectors.regex("/info.*")))
//				.paths(Predicates.not(PathSelectors.regex("/mappings.*")))
//				.paths(Predicates.not(PathSelectors.regex("/pause.*")))
//				.paths(Predicates.not(PathSelectors.regex("/heapdump.*")))
//				.paths(Predicates.not(PathSelectors.regex("/metrics.*")))
//				.paths(Predicates.not(PathSelectors.regex("/features.*")))
//				.paths(Predicates.not(PathSelectors.regex("/camel.*")))
//				.paths(Predicates.not(PathSelectors.regex("/model2.*")))
				.build();
		
		return docket;
    }
	
	@Bean
	public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
	    return new BeanPostProcessor() {

	        @Override
	        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
	            if (bean instanceof WebMvcRequestHandlerProvider) {
	                customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
	            }
	            return bean;
	        }

	        private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
	            List<T> copy = mappings.stream()
	                    .filter(mapping -> mapping.getPatternParser() == null)
	                    .collect(Collectors.toList());
	            mappings.clear();
	            mappings.addAll(copy);
	        }

	        @SuppressWarnings("unchecked")
	        private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
	            try {
	                Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
	                if(field != null) {
	                	field.setAccessible(true);
	                }
	                return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
	            } catch (IllegalArgumentException | IllegalAccessException e) {
	                throw new IllegalStateException(e);
	            }
	        }
	    };
	}
}
