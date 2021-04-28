import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class TelegramBotExtension extends TelegramLongPollingBot {
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
        SendMessage msg = SendMessage
                .builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(new ReplyKeyboardRemove(true))
                .build();
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    protected synchronized void sendMsgWithKeyboard(String chatId, String text, ReplyKeyboard keyboardMarkup) {
        SendMessage msg = SendMessage
                .builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(keyboardMarkup)
                .build();
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

    protected InlineKeyboardMarkup setMainKeyboardMarkup() {
        InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton employeeInfoBtn = new InlineKeyboardButton();
        employeeInfoBtn.setText("Информация о сотруднике");
        employeeInfoBtn.setCallbackData("Employee info");
        InlineKeyboardButton vacationInfoBtn = new InlineKeyboardButton();
        vacationInfoBtn.setText("Информация о моем отпуске");
        vacationInfoBtn.setCallbackData("Vacation info");
        InlineKeyboardButton vacationRulesBtn = new InlineKeyboardButton();
        vacationRulesBtn.setText("Информация о порядке оформления отпуска");
        vacationRulesBtn.setCallbackData("Vacation rules");
        List<InlineKeyboardButton> row0 = new ArrayList<>();
        row0.add(employeeInfoBtn);
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(vacationInfoBtn);
        row1.add(vacationRulesBtn);
        List<List<InlineKeyboardButton>> markupList = new ArrayList<>();
        markupList.add(row0);
        markupList.add(row1);
        replyKeyboardMarkup.setKeyboard(markupList);
        return replyKeyboardMarkup;
    }


}