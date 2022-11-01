package matches.organizer.service;
import matches.organizer.domain.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.List;
import java.util.Optional;

@Service
public class BotService extends TelegramLongPollingBot {

    private final MatchService matchService;
    Logger logger = LoggerFactory.getLogger(BotService.class);


    @Autowired
    public BotService(MatchService matchService) {
        this.matchService = matchService;
    }

    private boolean screaming = false;

    @Override
    public String getBotUsername() {
        // TODO: Externalizar a variable de entorno
        return "MatchOrganizer";
    }

    @Override
    public String getBotToken() {
        String token = System.getenv("TELEGRAM_BOT_TOKEN");
        return token != null ? token : "ERROR";
    }

    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var user = msg.getFrom();

        logger.info("TELEGRAM BOT: UserID: " + user.getId() + " Name:" + user.getFirstName() + " Wrote: " + msg.getText());

        if(msg.isCommand()){
            if (msg.getText().equals("/list")){
                List<Match> matches = matchService.getMatches();
                sendText(user.getId(), matches.toString());
            }
        }
    }

    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) // Who are we sending a message to
                .text(what).build();    // Message content

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}