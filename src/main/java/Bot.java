import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class Bot extends TelegramBotExtension {
    private final String id = "1705482445:AAHFkgPeFtdcmV1_FOA5AeUpqVVTyuc00ok";
    private final String name = "valera_mopsly_bot";
    ConversationStateMonitor stateMonitor = new ConversationStateMonitor();
    String string;

    public static void main(String[] args) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        Authorization.setUpEmployees();
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
                if (msg.getText().equals("/start")) {
                    sendMsg(msg.getChatId().toString(), "Здравствуйте, меня зовут Валера");
                }
                if (Authorization.isUserAuthorised(user.getId().toString()))
                    sendMsg(msg.getChatId().toString(), "Hello, " + user.getFirstName());
                else {
                    switch (stateMonitor.getState(user.getId().toString())) {
                        case NONE:
                            sendMsg(msg.getChatId().toString(), "Вы не авторизованы? Введите код сообщения");
                            stateMonitor.setState(msg.getChatId().toString(), ConversationStateMonitor.State.AWAIT_FOR_CODE);
                            string = Authorization.generateVerificationCode();
                            System.out.println(string);
                            break;
                        case AWAIT_FOR_CODE:
                            if (msg.getText().equals(string)) {
                                sendMsg(msg.getChatId().toString(), "Код принят");
                                stateMonitor.setState(msg.getChatId().toString(), ConversationStateMonitor.State.NONE);
                                Authorization.addUser(msg.getChatId().toString());
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

}
