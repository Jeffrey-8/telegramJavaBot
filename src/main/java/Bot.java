import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramBotExtention {
    private final String id = "1705482445:AAHFkgPeFtdcmV1_FOA5AeUpqVVTyuc00ok";
    private final String name = "valera_mopsly_bot";
    ConversationStateMonitor stateMonitor = new ConversationStateMonitor();

    public static void main(String[] args) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return id;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("update recieved");
        if (update.hasMessage()) {
            Message msg = update.getMessage();
            System.out.println(stateMonitor.getState(msg.getChatId().toString()));

            User user = update.getMessage().getFrom();
            if (msg.hasText()) {
                switch (stateMonitor.getState(user.getId().toString())) {
                    case NONE:
                        if (msg.getText().equals("/start")) {
                            sendMsg(msg.getChatId().toString(), "Здравствуйте, меня зовут Валера");
                        } else {
                            if (Authorization.isUserAuthorised(user.getId().toString()))
                                sendMsg(msg.getChatId().toString(), "Hello, " + user.getFirstName());
                            else {
                                sendMsg(msg.getChatId().toString(), "Sorry, you don't have permission to talk to me \n " +
                                        "Would you like to give me your phone number?");
                                stateMonitor.setState(msg.getChatId().toString(), ConversationStateMonitor.State.AWAIT_FOR_PHONE);
                        }
                        }
                        break;
                    case AWAIT_FOR_PHONE:
                        sendMsg(msg.getChatId().toString(), "enter your phone number");
                        stateMonitor.setState(msg.getChatId().toString(), ConversationStateMonitor.State.PHONE_ACCEPTED);
                        break;
                    case  PHONE_ACCEPTED:
                        sendMsg(msg.getChatId().toString(),"Access Granted");
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
