package com.lwc.naviblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebContextConfig implements WebMvcConfigurer {

    // 跨域配置
    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/smallapple/**")
                        // /apple/**表示在磁盘apple目录下的所有资源会被解析为以下的路径
                        .addResourceLocations("file:G:/itemsource/smallapple/") //媒体资源
                        .addResourceLocations("classpath:/META-INF/resources/");  //swagger2页面

                registry.addResourceHandler("/images/**")
                        .addResourceLocations("file:E:\\Maven\\exercise_project\\navi-blog\\src\\main\\resources\\static\\images\\");
            }

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8080","http://localhost:9000","https://fonts.gstatic.com/**") //允许跨域的域名，可以用*表示允许任何域名使用
                        .allowedMethods("*") //允许任何方法（post、get等）
                        .allowedHeaders("*") //允许任何请求头
                        .allowCredentials(true) //带上cookie信息
                        .exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L); //maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
            }
        };
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8080");
        corsConfiguration.addAllowedOrigin("http://localhost:9000");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addExposedHeader(HttpHeaders.SET_COOKIE);
        corsConfiguration.setMaxAge(3600L);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

}
