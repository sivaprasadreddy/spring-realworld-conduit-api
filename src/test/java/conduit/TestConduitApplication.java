package conduit;

import org.springframework.boot.SpringApplication;

public class TestConduitApplication {

    public static void main(String[] args) {
        SpringApplication.from(ConduitApplication::main)
                .with(ContainersConfig.class)
                .run(args);
    }
}
