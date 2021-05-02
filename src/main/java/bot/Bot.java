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
import servises.EmployeeInfoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    EmployeeInfoService employeeInfoService = new EmployeeInfoService();
    Authorization authorization = new Authorization();


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
            if (authorization.isUserAuthorised(user.getId().toString())) {
                switch (stateMonitor.getState(msg.getChatId().toString())) {
                    case AWAIT_FOR_EMPLOYEE:
                        if (employeeInfoService.isNameCorrect(msg.getText())) {
                            List<UserVacation> employeesInfo = repository.findAllByLastName(msg.getText());
                            if (employeesInfo == null || employeesInfo.isEmpty()) {
                                sendMsg(msg.getChatId().toString(), "По вашему запросу совпадений не найдено");
                                break;
                            }
                            if (employeesInfo.size() > 1) {
                                sendMsg(msg.getChatId().toString(), employeeInfoService.getUsersListInfoMessage(employeesInfo));
                                employeeInfoService.getSearchResults().put(msg.getChatId().toString(), employeesInfo);
                                stateMonitor.setState(msg.getChatId().toString(), ConversationStateMonitor.State.AWAIT_FOR_EMPLOYEE_NUM);
                            } else {
                                sendMsg(msg.getChatId().toString(), employeeInfoService.getUserInfoMessage(employeesInfo.get(0)));
                                stateMonitor.setState(msg.getChatId().toString(), ConversationStateMonitor.State.NONE);
                            }
                        }
                        break;
                    case AWAIT_FOR_EMPLOYEE_NUM:
                        if (employeeInfoService.isNumberCorrect(msg.getText())) {
                            int pos = Integer.parseInt(msg.getText()) - 1;
                            List<UserVacation> userInfoList = employeeInfoService.getSearchResults().get(msg.getChatId().toString());
                            if (pos < userInfoList.size()) {
                                sendMsg(msg.getChatId().toString(), employeeInfoService.getUserInfoMessage(userInfoList.get(pos)));
                                stateMonitor.setState(msg.getChatId().toString(), ConversationStateMonitor.State.NONE);
                                employeeInfoService.getSearchResults().remove(msg.getChatId().toString());
                            } else
                                sendMsg(msg.getChatId().toString(), "Некорректный номер");
                        } else
                            sendMsg(msg.getChatId().toString(), "Некорректный номер");
                        break;
                        // если кто знает, как избавиться от этих двух элсов, то пишите
                    default:
                        sendMsgWithKeyboard(msg.getChatId().toString(), "Чем могу вам помочь?", setMainKeyboardMarkup());
                        break;
                }
            } else {
                switch (stateMonitor.getState(user.getId().toString())) {
                    case NONE:
                        sendMsgWithKeyboard(msg.getChatId().toString(), "Вы не авторизованы. \n" +
                                "Для доступа к функционалу введите свой номер телефона", setPhoneKeyboard());
                        stateMonitor.setState(msg.getChatId().toString(), ConversationStateMonitor.State.AWAIT_FOR_PHONE);
                        break;
                    case AWAIT_FOR_PHONE:
                        sendMsg(msg.getChatId().toString(), "Вам отправлен код подтверждения. Введите его, чтобы продолжить");
                        authorization.addUser(msg.getChatId().toString());
                        verificationCode = authorization.generateVerificationCode(msg.getChatId().toString());
                        System.out.println(msg.getChatId() + " " + verificationCode);
                        stateMonitor.setState(msg.getChatId().toString(), ConversationStateMonitor.State.AWAIT_FOR_CODE);
                        break;
                    case AWAIT_FOR_CODE:
                        if (msg.hasText() && authorization.verifyCode(msg.getChatId().toString(), msg.getText())) {
                            sendMsg(msg.getChatId().toString(), "Код принят");
                            sendMsgWithKeyboard(msg.getChatId().toString(), "Чем могу вам помочь?", setMainKeyboardMarkup());
                            stateMonitor.setState(msg.getChatId().toString(), ConversationStateMonitor.State.NONE);
                            authorization.authoriseUser(msg.getChatId().toString());
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


}
