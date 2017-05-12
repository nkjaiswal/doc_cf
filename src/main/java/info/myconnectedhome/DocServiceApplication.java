package info.myconnectedhome;

import java.io.File;
import java.util.Properties;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import info.myconnectedhome.model.Credential;

@SpringBootApplication
@Configuration
@EnableJpaRepositories("info.myconnectedhome.repository")
@ComponentScan({"info.myconnectedhome.controller","info.myconnectedhome.repository"})
public class DocServiceApplication {

	public static void main(String[] args) throws JSONException {
		SpringApplication application = new SpringApplication(DocServiceApplication.class);
		System.out.println(System.getProperty("user.dir"));
		
        application.run(args);
	}
	
}
