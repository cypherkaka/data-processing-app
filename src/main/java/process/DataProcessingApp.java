package process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import process.interceptor.DataProcessingInterceptor;

/**
 * Staring point of Spring-boot application which will provide different REST end-points
 */
@Configuration
@SpringBootApplication
public class DataProcessingApp extends WebMvcConfigurerAdapter {

    @Autowired
    DataProcessingInterceptor dataProcessingInterceptor;

    /**
     * Adding common interceptors to end-points
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(dataProcessingInterceptor).addPathPatterns("/data-processing-app/**");
    }

    public static void main(String... args) {
        SpringApplication.run(DataProcessingApp.class, args);
    }
}
