package io.unmeshed.sdk.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"io.unmeshed.client", "io.unmeshed.sdk.samples"})
public class UnmeshedSpringBootWorkersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnmeshedSpringBootWorkersApplication.class, args);
	}

}
