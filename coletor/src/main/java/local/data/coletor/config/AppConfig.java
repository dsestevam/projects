package local.data.coletor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;

@Configuration
@ImportResource({ "classpath:environment.xml" })
@ComponentScan(basePackages = { "local.data.coletor" })
@Import({ PersistenceConfig.class, WebAppConfig.class })
public class AppConfig {
	@Value("classpath:logback.xml")
	private Resource logbackConfig;

	private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

	public AppConfig() {
		logger.info("Hello, Mr. AppConfig!");
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("messages");
		return source;
	}

	@Bean
	public LogbackConfig logbackConfigurer() {
		LogbackConfig retval = new LogbackConfig();
		retval.setLocation(logbackConfig);
		return retval;
	}
}
