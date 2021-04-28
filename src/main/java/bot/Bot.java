package bot;

import models.Role;
import models.State;
import models.UserVacation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
public class Bot extends TelegramBotExtension {
//    private final String id = "1705482445:AAHFkgPeFtdcmV1_FOA5AeUpqVVTyuc00ok";
//    private final String name = "valera_mopsly_bot";


    @Value("${bot.name}")
    private String name /*= "valera_mopsly_bot"*/;

    @Value("${bot.token}")
    private String id /*= "1705482445:AAHFkgPeFtdcmV1_FOA5AeUpqVVTyuc00ok"*/;



    ConversationStateMonitor stateMonitor = new ConversationStateMonitor();
    String string;

//    public Bot() {
//        try {
//            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(/*DefaultBotSession.class*/);
//            telegramBotsApi.registerBot(new Bot());
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//        Authorization.setUpEmployees();
//    }


//    public static void main(String[] args) {
//        try {
//            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(/*DefaultBotSession.class*/);
//            telegramBotsApi.registerBot(new Bot.Bot());
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//        Bot.Authorization.setUpEmployees();
//    }

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
                if (msg.getText().equals("/start")) {
                    sendMsg(msg.getChatId().toString(), "Здравствуйте, меня зовут Валера");
                }
            }
                if (Authorization.isUserAuthorised(user.getId().toString()))
                    sendMsg(msg.getChatId().toString(), "Hello, " + user.getFirstName());
                else {
                    switch (stateMonitor.getState(user.getId().toString())) {
                        case NONE:
                            sendMsgWithKeyboard(msg.getChatId().toString(), "Вы не авторизованы. \n"+
                                    "Для доступа к функционалу введите свой номер телефона",setPhoneKeyboard());
                            stateMonitor.setState(msg.getChatId().toString(), ConversationStateMonitor.State.AWAIT_FOR_PHONE);
                            break;
                        case AWAIT_FOR_PHONE:

                            sendMsg(msg.getChatId().toString(),"Вам отправлен код подтверждения. Введите его, чтобы продолжить");
                            Authorization.addUser(msg.getChatId().toString());
                            string = Authorization.generateVerificationCode(msg.getChatId().toString());
                            System.out.println(msg.getChatId()+" "+string);
                            stateMonitor.setState(msg.getChatId().toString(), ConversationStateMonitor.State.AWAIT_FOR_CODE);
                            break;
                        case AWAIT_FOR_CODE:
                            if (msg.hasText()&& Authorization.verifyCode(msg.getChatId().toString(),msg.getText())) {
                                sendMsg(msg.getChatId().toString(), "Код принят");
                                stateMonitor.setState(msg.getChatId().toString(), ConversationStateMonitor.State.NONE);
                                Authorization.authoriseUser(msg.getChatId().toString());
                            } else {
                                sendMsg(msg.getChatId().toString(), "Неверный код");
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }


}
