package matches.organizer.service;
import matches.organizer.domain.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.List;

@Service
public class BotService extends TelegramLongPollingBot {

    private final MatchService matchService;

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
        // TODO: Externalizar a variable de entorno
        return "5799926198:AAEF5QDq2GpHNC6alswpIAiMqMN3e7WH84o";
    }

    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var user = msg.getFrom();

        System.out.println("TELEGRAM BOT: UserID: " + user.getId() + " Name:" + user.getFirstName() + " Wrote: " + msg.getText());

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