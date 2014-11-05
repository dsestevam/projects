package local.data.coletor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:database.xml"})
public class PersistenceConfig {
	
   private static final Logger logger = LoggerFactory.getLogger(PersistenceConfig.class);
   
   public PersistenceConfig() {
      logger.info("Hello, Mr. PersistenceConfig!");
   }

}
