package matches.organizer;

import matches.organizer.storage.InMemoryMatchRepository;
import matches.organizer.storage.MatchRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MatchesOrganizerApp {

	public static void main(String[] args) {
		SpringApplication.run(MatchesOrganizerApp.class, args);
	}
}