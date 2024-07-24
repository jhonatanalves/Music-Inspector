package com.example.MusicInspector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.MusicInspector.principal.Principal;
import com.example.MusicInspector.repository.CantorRepository;

@SpringBootApplication
public class MusicInspectorApplication implements CommandLineRunner {

	@Autowired
	private CantorRepository cantorRepository;

	public static void main(String[] args) {
		SpringApplication.run(MusicInspectorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal(cantorRepository);
		principal.exibeMenu();

	}
}
