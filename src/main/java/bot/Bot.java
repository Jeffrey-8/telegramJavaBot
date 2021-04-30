package bot;

import models.UserVacation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import repositories.UserVacationRepository;
import servises.MessageService;

import java.util.ArrayList;
import java.util.List;


@Component
public class Bot extends TelegramBotExtension {
//    private final String id = "1705482445:AAHFkgPeFtdcmV1_FOA5AeUpqVVTyuc00ok";
//    private final String name = "valera_mopsly_bot";

    @Autowired
    UserVacationRepository repository;

    @Value("${bot.name}")
    private String name /*= "valera_mopsly_bot"*/;

    @Value("${bot.token}")
    private String id /*= "1705482445:AAHFkgPeFtdcmV1_FOA5AeUpqVVTyuc00ok"*/;



    ConversationStateMonitor stateMonitor = new ConversationStateMonitor();
    MessageService messageService = new MessageService();
    String verificationCode;

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
            User user = update.getMessage().getFrom();
            if (msg.hasText()) {
                if (msg.getText().equals("/start")) {
                    sendMsg(msg.getChatId().toString(), "Здравствуйте, меня зовут Валера");
                }
            }
            if (Authorization.isUserAuthorised(user.getId().toString())) {
                switch (stateMonitor.getState(msg.getChatId().toString())) {
                    case AWAIT_FOR_EMPLOYEE:

                        String[] candidateEmployee = splitLastNameFirstName(msg);

                        if (candidateEmployee.length>1){ //если передвли боту и firstName и Lastname

                            //FIXME: @FRO: если пользователь ввдет не в том порядке или Иванов И.И. то что тогда
                            List<UserVacation> employeesInfo = repository.findAllByLastNameAndFirstName(candidateEmployee[0], candidateEmployee[1]);
                            if(employeesInfo!=null)
                            {
                                sendMsg(msg.getChatId().toString(), messageService.getUserInfoMessage(employeesInfo));
                                if( employeesInfo.size()==1){
                                    stateMonitor.setState(msg.getChatId().toString(), ConversationStateMonitor.State.NONE);
                                }
                            }

                        } else { //если боту передали только LastName
                            List<UserVacation> employeesInfo = repository.findAllByLastName(candidateEmployee[0]);

                            sendMsg(msg.getChatId().toString(), messageService.getUserInfoMessage(employeesInfo));
                            if( employeesInfo.size()==1) {
                                stateMonitor.setState(msg.getChatId().toString(), ConversationStateMonitor.State.NONE);
                            }
                        }
                        break;
                    default:
                        sendMsgWithKeyboard(msg.getChatId().toString(), "Чем могу вам помочь?", setMainKeyboardMarkup());
                        break;
                }
            }
            else {
                switch (stateMonitor.getState(user.getId().toString())) {
                    case NONE:
                        sendMsgWithKeyboard(msg.getChatId().toString(), "Вы не авторизованы. \n" +
                                "Для доступа к функционалу введите свой номер телефона", setPhoneKeyboard());
                        stateMonitor.setState(msg.getChatId().toString(), ConversationStateMonitor.State.AWAIT_FOR_PHONE);
                        break;
                    case AWAIT_FOR_PHONE:
                        sendMsg(msg.getChatId().toString(), "Вам отправлен код подтверждения. Введите его, чтобы продолжить");
                        Authorization.addUser(msg.getChatId().toString());
                        verificationCode = Authorization.generateVerificationCode(msg.getChatId().toString());
                        System.out.println(msg.getChatId() + " " + verificationCode);
                        stateMonitor.setState(msg.getChatId().toString(), ConversationStateMonitor.State.AWAIT_FOR_CODE);
                        break;
                    case AWAIT_FOR_CODE:
                        if (msg.hasText() && Authorization.verifyCode(msg.getChatId().toString(), msg.getText())) {
                            sendMsg(msg.getChatId().toString(), "Код принят");
                            sendMsgWithKeyboard(msg.getChatId().toString(), "Чем могу вам помочь?", setMainKeyboardMarkup());
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
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            System.out.println(callbackQuery.getData());
            switch (callbackQuery.getData()) {
                case "Employee info":
                    sendMsg(callbackQuery.getFrom().getId().toString(), "Введите ФИО сотрудника");
                    stateMonitor.setState(callbackQuery.getFrom().getId().toString(), ConversationStateMonitor.State.AWAIT_FOR_EMPLOYEE);
                    break;
                case "Vacation info":
                    break;
                case "Vacation rules":
                    sendMsg(callbackQuery.getFrom().getId().toString(),
                            "Для того чтобы уйти в отпуск напишите заявление");
                    break;
                default:
                    break;
            }





        }
    }

    String[] splitLastNameFirstName(Message message) {
        String msg = message.getText();
        String[] result = msg.split(" ");
        return result;
    }
}
