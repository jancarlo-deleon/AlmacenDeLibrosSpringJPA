package com.jdeleon.challengeliteratura;

import com.jdeleon.challengeliteratura.principal.Principal;
import com.jdeleon.challengeliteratura.repository.AutorRepository;
import com.jdeleon.challengeliteratura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeliteraturaApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeliteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository,autorRepository);
		principal.muestraElMenu();
	}
}
