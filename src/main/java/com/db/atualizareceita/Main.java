package com.db.atualizareceita;

import com.db.atualizareceita.runner.IncomeSynchronizationRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {
	@Autowired
    IncomeSynchronizationRunner incomeSynchronizationRunner;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		incomeSynchronizationRunner.updateIncome(args);
	}
}