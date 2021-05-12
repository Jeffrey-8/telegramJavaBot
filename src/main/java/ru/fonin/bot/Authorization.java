package ru.fonin.bot;

import ru.fonin.models.AuthState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Contact;
import ru.fonin.repositories.AuthRepository;
import ru.fonin.models.UserAuth;


import java.util.*;

@Service
public class Authorization {

    @Autowired
    AuthRepository authRepository;


//    private   Map<String,User> users = new HashMap<>();

//    public void setUpEmployees() {
//        List<UserAuth> usersQuerry = authRepository.findAll();
//        for (UserAuth user : usersQuerry) {
//            users.put(user.getTelegramId(), new User(user.getTelegramId(), user.getPhoneNumber(), user.isLoggedIn()));
//        }
//    }


//    public String chan


    public boolean addUser(String chatId, Contact phoneNumber, String textPhoneNumber, String verificationCode) {
//        users.put(id, new User(id, "", false));

        String correctNumber="";

        if (phoneNumber==null){
            correctNumber=textPhoneNumber.replaceAll("[^0-9]","");
            if (correctNumber.startsWith("8"))//TODO: нужно ли отлавливать восьмерку  могут же быть другие коды
                correctNumber=correctNumber.replaceFirst("8","7");

        } else {
            correctNumber=phoneNumber.getPhoneNumber();
        }

        System.out.println(correctNumber);

        UserAuth userAuth = authRepository.findUserAuthByPhoneNumber(correctNumber);


        //Если номера нет в вайтлисте...
        if (userAuth == null){
            return false;
        }
        else{
            authRepository.UpdateUserAuth(correctNumber,AuthState.NOT_AUTHORISED,verificationCode,chatId);//TODO: @FRO: захэшировать
        }
        return true;
    }


    public void authoriseUser(String chatId) {
//        users.get(id).setLoggedIn(true);
        authRepository.UpdateState(chatId, AuthState.AUTHORISED);
    }

    public boolean isUserAuthorised(String chatId) {
//        // this wil be the way to do it
////    UserAuth user = authRepository.findUserAuthById(id);
////    if(user != null && user.isLoggedIn())
////        return true;
////    return false;
//        return users.containsKey(id) && users.get(id).isLoggedIn();

        UserAuth candidateAuth = authRepository.findUserAuthByChatId(chatId);
        if (candidateAuth == null)
            return false;


        if (candidateAuth.getAuthState() == AuthState.AUTHORISED)
            return true;
        else return false;
    }



public  String generateVerificationCode(String chatId){
    Random random = new Random();
    String code = String.format("%04d",random.nextInt(10000));
    authRepository.UpdateVerificationCode(chatId, code);
    System.out.println(code);
    //TODO Вставить заглушку для проверки прототипа
//    return code;
    return "0000"; // для проверки
}
public  boolean verifyCode(String chatId, String code){
//    return  code.equals(users.get(id).getVerificationCode());
    UserAuth userAuth = authRepository.findUserAuthByChatId(chatId);
    //TODO: тут надо подумать (да и везде)
    // может ли на этом этапе быть неск-ко  пользователей с одинаковым chatId
    if(userAuth.getVerificationCode().equals(code))
        return true;
    else
        return false;

//return  true; //FIXME: temporary auth plug
}

}

class  User {
    private String id;
    private String phoneNumber;
    private String verificationCode;
    private boolean loggedIn = false;

    public User(String id, String phoneNumber, boolean loggedIn) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.loggedIn = loggedIn;
    }

    public String getId() {
        return id;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }


    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}