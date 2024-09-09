package pe.edu.usmp.bot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MsEpicsBotApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MsEpicsBotApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MsEpicsBotApplication.class, args);
	}
	
}
