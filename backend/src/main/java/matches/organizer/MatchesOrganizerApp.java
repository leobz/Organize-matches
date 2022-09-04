package matches.organizer;

import matches.organizer.storage.InMemoryMatchRepository;
import matches.organizer.storage.MatchRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MatchesOrganizerApp {
	public static void main(String[] args) {
		SpringApplication.run(MatchesOrganizerApp.class, args);
	}
}