package com.mrvelibor.metchat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mrvelibor.metchat.podaci.MetCookie;
import com.mrvelibor.metchat.podaci.MetGroup;
import com.mrvelibor.metchat.podaci.MetMessage;
import com.mrvelibor.metchat.podaci.MetUser;
import com.mrvelibor.metchat.repos.MetCookieRepository;
import com.mrvelibor.metchat.repos.MetGroupRepository;
import com.mrvelibor.metchat.repos.MetMessageRepository;
import com.mrvelibor.metchat.repos.MetUserRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private MetUserRepository userRepo;
	@Autowired
	private MetCookieRepository cookieRepo;
	@Autowired
	private MetGroupRepository groupRepo;
	@Autowired
	private MetMessageRepository messageRepo;

	@Override
	public void run(String... args) throws Exception {
		
		// MetUser
		userRepo.deleteAll();

		MetUser user1 = userRepo.save(new MetUser("User 1", "1234", 1));
		MetUser user2 = userRepo.save(new MetUser("User 2", "2234", 2));
		MetUser user3 = userRepo.save(new MetUser("User 3", "3234", 3));
		MetUser user4 = userRepo.save(new MetUser("Velja", "0234", 4));
		MetUser user5 = userRepo.save(new MetUser("Veljaa", "00234", 5));
		MetUser user6 = userRepo.save(new MetUser("Grupa 1", "00234", 6));
		userRepo.save(new MetUser("ASD", "00234", 6));
		userRepo.save(new MetUser("GGG", "00234", 6));
		userRepo.save(new MetUser("TYGVasd", "00234", 6));

		System.out.println("Users found with findAll():");
		System.out.println("-------------------------------");
		for (MetUser user : userRepo.findAll()) {
			System.out.println(user);
		}
		System.out.println();
		
		user1.favorites.add(user2.username);
		user1 = userRepo.save(user1);

		System.out.println("Users found with findByUsernameStartingWithIgnoreCase(\"user\")");
		System.out.println("--------------------------------");
		for (MetUser user : userRepo.findByUsernameStartingWithIgnoreCase("user")) {
			System.out.println(user);
		}
		System.out.println();
		
		
		// MetGroup
		groupRepo.deleteAll();
		
		MetGroup group1 = groupRepo.save(new MetGroup("Grupa 1"));
		MetGroup group2 = groupRepo.save(new MetGroup("Grupa 2"));
		MetGroup group3 = groupRepo.save(new MetGroup("Grupica"));
		MetGroup group4 = groupRepo.save(new MetGroup("PaGru"));

		System.out.println("Groups found with findAll():");
		System.out.println("-------------------------------");
		for (MetGroup group : groupRepo.findAll()) {
			System.out.println(group);
		}
		System.out.println();
		
		group1.users.add(user1.username);
		user1.groups.add(group1.name);		
		group1.users.add(user2.username);
		user2.groups.add(group1.name);		
		user1 = userRepo.save(user1);
		user2 = userRepo.save(user2);
		group1 = groupRepo.save(group1);

		System.out.println("Groups found with findByNameStartingWithIgnoreCase(\"grup\")");
		System.out.println("--------------------------------");
		for (MetGroup group : groupRepo.findByNameStartingWithIgnoreCase("grup")) {
			System.out.println(group);
		}
		System.out.println();
		
		
		// MetCookie
		cookieRepo.deleteAll();

		MetCookie cookie1 = cookieRepo.save(new MetCookie(user1));
		MetCookie cookie2 = cookieRepo.save(new MetCookie(user2));
		MetCookie cookie3 = cookieRepo.save(new MetCookie(user1));

		System.out.println("Cookies found with findAll():");
		System.out.println("-------------------------------");
		for (MetCookie cookie : cookieRepo.findAll()) {
			System.out.println(cookie);
		}
		System.out.println();

		System.out.println("Cookies found with findByUsername(user1.username)");
		System.out.println("--------------------------------");
		for (MetCookie cookie : cookieRepo.findByUsername(user1.username)) {
			System.out.println(cookie);
		}
		System.out.println();
		
		
		// MetMessage
		messageRepo.deleteAll();

		MetMessage message1 = messageRepo.save(new MetMessage(user1, user6, "Poruka 1"));
		MetMessage message2 = messageRepo.save(new MetMessage(user2, user1, "Poruka 2"));
		MetMessage message3 = messageRepo.save(new MetMessage(user1, group1, "Poruka 3"));
		MetMessage message4 = messageRepo.save(new MetMessage(user2, group1, "Poruka 4"));

		messageRepo.save(new MetMessage(user2, user1, "Poruka 2"));
		messageRepo.save(new MetMessage(user2, user1, "Poruka 24"));
		messageRepo.save(new MetMessage(user1, user2, "Poruka 263"));
		messageRepo.save(new MetMessage(user2, user1, "Poruka 234"));
		messageRepo.save(new MetMessage(user1, user2, "Poruka 662"));
		messageRepo.save(new MetMessage(user1, user2, "Poruka 7642"));

		System.out.println("Messages found with findAll():");
		System.out.println("-------------------------------");
		for (MetMessage message : messageRepo.findAll()) {
			System.out.println(message);
		}
		System.out.println();

		System.out.println("Messages found with findByUsers(user1.username, user2.username)");
		System.out.println("--------------------------------");
		for (MetMessage message : messageRepo.findByUsers(user1.username, user2.username)) {
			System.out.println(message);
		}
		System.out.println();

		System.out.println("Messages found with findByGroup(group1.name)");
		System.out.println("--------------------------------");
		for (MetMessage message : messageRepo.findByGroup(group1.name)) {
			System.out.println(message);
		}
		System.out.println();
		
		
		// MetUser
		System.out.println("Users found with findAll():");
		System.out.println("-------------------------------");
		for (MetUser user : userRepo.findAll()) {
			System.out.println(user);
		}
		System.out.println();
		
		System.out.println(Hashish.GenerateCookie());
		System.out.println(Hashish.HashPassword("pass"));
	}

}
