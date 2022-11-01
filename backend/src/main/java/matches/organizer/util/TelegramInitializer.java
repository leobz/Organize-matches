package matches.organizer.util;

import matches.organizer.MatchesOrganizerApp;
import matches.organizer.service.BotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;

@Component
public class TelegramInitializer {

    private final BotService bot;

    @Autowired
    public TelegramInitializer(BotService bot) {
        this.bot = bot;
    }

    @PostConstruct
    public void initializeBot(){
        Logger logger = LoggerFactory.getLogger(MatchesOrganizerApp.class);
        logger.info("Initializing Telegram Bot");

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
            logger.info("Telegram Bot Initialized");
        } catch (TelegramApiException e) {
            logger.error("Can't Initializing Telegram Bot ", e);
        }

    }
}
