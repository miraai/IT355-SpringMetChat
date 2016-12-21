package com.mrvelibor.metchat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mrvelibor.metchat.podaci.MetUser;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private MetUserRepository repository;

	@Override
	public void run(String... args) throws Exception {
		repository.deleteAll();

		// save a couple of customers
		repository.save(new MetUser("Velja", "123456"));
		repository.save(new MetUser("Velja", "123456"));
		repository.save(new MetUser("Selja", "8888"));

		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (MetUser user : repository.findAll()) {
			System.out.println(user);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Customer found with Velja");
		System.out.println("--------------------------------");
		System.out.println(repository.findByUsername("Velja"));
	}

}
