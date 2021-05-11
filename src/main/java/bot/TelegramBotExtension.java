package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
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
        SendMessage msg = new SendMessage(chatId,text)
                .setReplyMarkup(new ReplyKeyboardRemove().setSelective(true));

        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    protected synchronized void sendMsgWithKeyboard(String chatId, String text, ReplyKeyboard keyboardMarkup) {
        SendMessage msg = new SendMessage(chatId,text)
                .setReplyMarkup(keyboardMarkup);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    protected synchronized void sendFile(String chatId,String path){
        SendDocument sendDocument = new SendDocument()
                .setChatId(chatId)
                .setDocument(new File(path));
        //FIXME адеквтаность сюда
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    protected ReplyKeyboard setPhoneKeyboard() {
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

    protected ReplyKeyboard setMainKeyboardMarkup() {
        InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton employeeInfoBtn = new InlineKeyboardButton();
        employeeInfoBtn.setText("Информация о сотруднике");
        employeeInfoBtn.setCallbackData("Employee info");
        InlineKeyboardButton instructionsBtn = new InlineKeyboardButton();
        instructionsBtn.setText("Получить список инструкций");
        instructionsBtn.setCallbackData("Instructions");
        InlineKeyboardButton companyAddressBtn = new InlineKeyboardButton();
        companyAddressBtn.setText("Реквизиты компании");
        companyAddressBtn.setCallbackData("CompanyAddress");
        List<InlineKeyboardButton> row0 = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row0.add(employeeInfoBtn);
        row1.add(instructionsBtn);
        row2.add(companyAddressBtn);
        List<List<InlineKeyboardButton>> markupList = new ArrayList<>();
        markupList.add(row0);
        markupList.add(row1);
        markupList.add(row2);
        replyKeyboardMarkup.setKeyboard(markupList);
        return replyKeyboardMarkup;
    }


}