package ch.pa5.greenfit;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		servers = {
				@Server(url = "https://greenfit.app")
		}
)
@SpringBootApplication
public class GreenfitApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenfitApplication.class, args);
	}

}
