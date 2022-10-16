package matches.organizer.service;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
        return "TODO";
    }

    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var user = msg.getFrom();

        System.out.println("TELEGRAM BOT: UserID: " + user.getId() + " Name:" + user.getFirstName() + " Wrote: " + msg.getText());

        if(msg.isCommand()){
            if (msg.getText().equals("/list")){
                JsonObject allMatches = new JsonObject();
                JsonArray matchesArray = new JsonArray();
                matchService.getMatches().forEach(match -> matchesArray.add(JsonParser.parseString(match.toJsonString())));
                allMatches.add("matches", matchesArray);
                var response = allMatches.toString();
                sendText(user.getId(), response);
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