package local.data.coletor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;


public class LogbackConfig implements ApplicationContextAware, InitializingBean {
   private Logger logger = LoggerFactory.getLogger(this.getClass());
   private Resource location;
   private ApplicationContext applicationContext;

   public void setLocation(Resource location) {
      this.location = location;
   }

   @Override
   public void afterPropertiesSet() throws Exception {
      configureLogback();
      String [] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
      String profile = "default";
      if(activeProfiles.length > 0) {
         profile = activeProfiles[0];
      }
      logger.info("logback set up to {}", profile);
   }

   public void configureLogback() throws Exception {
      JoranConfigurator configurator = new JoranConfigurator();
      LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
      loggerContext.reset();
      configurator.setContext(loggerContext);
      configurator.doConfigure(location.getInputStream());
   }

   @Override
   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      this.applicationContext = applicationContext;
   }
}
