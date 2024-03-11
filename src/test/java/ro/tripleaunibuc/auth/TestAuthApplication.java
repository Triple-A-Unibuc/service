package ro.tripleaunibuc.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import ro.tripleaunibuc.infrastructure.bootstrap.TripleAProject;

@TestConfiguration(proxyBeanMethods = false)
public class TestAuthApplication {

    @Bean
    @ServiceConnection(name = "redis")
    GenericContainer<?> redisContainer() {
        return new GenericContainer<>(DockerImageName.parse("redis:latest")).withExposedPorts(6379);
    }

    public static void main(String[] args) {
        SpringApplication.from(TripleAProject::main).with(TestAuthApplication.class).run(args);
    }

}
