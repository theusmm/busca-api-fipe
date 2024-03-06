package br.com.theusmm.buscafipeapi;

import br.com.theusmm.buscafipeapi.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BuscafipeapiApplication implements CommandLineRunner {

	public static void main(String[] args)
	{
		SpringApplication.run(BuscafipeapiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
