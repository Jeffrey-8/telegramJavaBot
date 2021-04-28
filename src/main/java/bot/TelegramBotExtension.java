package bot;

import models.Role;
import models.State;
import models.UserVacation;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import repositories.UserVacationRepository;
import test.RepositoryTest.RepositoryTest;

import java.util.ArrayList;
import java.util.List;


public class TelegramBotExtension extends TelegramLongPollingBot {

    @Autowired
    UserVacationRepository repository;


    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    protected synchronized void sendMsg(String chatId, String text) {
//        SendMessage msg = SendMessage
//                .builder()
//                .chatId(chatId)
//                .text(text)
//                .replyMarkup(new ReplyKeyboardRemove(true))
//                .build();


//        repositoryTest.saveTest();

        SendMessage msg = new SendMessage(chatId,text)
                .setReplyMarkup(new ReplyKeyboardRemove().setSelective(true));

        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    protected synchronized void sendMsgWithKeyboard(String chatId, String text, ReplyKeyboardMarkup keyboardMarkup) {
//        SendMessage msg = SendMessage
//                .builder()
//                .chatId(chatId)
//                .text(text)
//                .replyMarkup(keyboardMarkup)
//                .build();

        SendMessage msg = new SendMessage(chatId,text)
                .setReplyMarkup(keyboardMarkup);


        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    protected ReplyKeyboardMarkup setPhoneKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardButtons = new KeyboardRow();
        KeyboardButton phoneBtn = new KeyboardButton();
        phoneBtn.setText("Отправить мой номер");
        phoneBtn.setRequestContact(true);
        keyboardButtons.add(phoneBtn);
        keyboard.add(keyboardButtons);
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }
}