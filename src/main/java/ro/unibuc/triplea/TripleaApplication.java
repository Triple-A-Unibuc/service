package ro.unibuc.triplea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class TripleaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripleaApplication.class, args);
	}

}
