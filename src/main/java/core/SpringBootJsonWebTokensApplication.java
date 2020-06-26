package core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.kaiso.relmongo.config.EnableRelMongo;

@SpringBootApplication
@EnableRelMongo
public class SpringBootJsonWebTokensApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJsonWebTokensApplication.class, args);
	}

}
