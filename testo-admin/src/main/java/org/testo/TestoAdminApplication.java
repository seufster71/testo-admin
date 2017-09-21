package org.testo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
@ComponentScan(basePackages = {"org.testo"})
public class TestoAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestoAdminApplication.class, args);
	}
}
