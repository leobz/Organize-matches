package matches.organizer;

import matches.organizer.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Component
public class MatchesOrganizerApp {

	@Autowired
	private BotService bot;

	public static void main(String[] args) {
		SpringApplication.run(MatchesOrganizerApp.class, args);
	}

	@PostConstruct
	public void initializeBot() throws TelegramApiException {
		TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
		botsApi.registerBot(bot);
	}
}