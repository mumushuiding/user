package com.lt.cloud;

import java.lang.reflect.Method;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@SpringBootApplication
@EnableJpaRepositories(value="com.lt.cloud.data.jpa")
@EnableCaching
//@EnableDiscoveryClient
public class SpringCloudUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudUserApplication.class, args);
	}
	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config=new CorsConfiguration();
		config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
      
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
	}
	 /**
     * redis缓存对象集合中，缓存是以 key-value 形式保存的。当不指定缓存的 key 时，SpringBoot 会使用 SimpleKeyGenerator 生成 key。
     * @return
     */
    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };

    }


}

