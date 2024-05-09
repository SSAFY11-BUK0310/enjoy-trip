package enjoytrip.global.config;

import enjoytrip.global.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FilterConfig implements WebMvcConfigurer {

  @Bean
  public FilterRegistrationBean<LoginCheckFilter> loginCheckFilter() {
    FilterRegistrationBean<LoginCheckFilter> bean = new FilterRegistrationBean<>();
    bean.setFilter(new LoginCheckFilter());
    bean.addUrlPatterns("/*");
    return bean;
  }
}
