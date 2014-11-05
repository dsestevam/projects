package local.data.coletor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(basePackages = {"local.data.coletor"})
@EnableWebMvc
public class WebAppConfig extends WebMvcConfigurerAdapter {
   private static final Logger logger = LoggerFactory.getLogger(WebAppConfig.class);
   
   public WebAppConfig() {
      logger.info("Hello, Mr. WebAppConfig!");
   }
   
   @Override
   public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
      configurer.enable();
   }
}
