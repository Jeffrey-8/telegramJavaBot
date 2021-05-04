package bot;

import models.AuthState;
import org.apache.http.auth.AUTH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import repositories.AuthRepository;
import models.UserAuth;


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

    public void addUser(String chatId, String phoneNumber, String verificationCode) {
//        users.put(id, new User(id, "", false));

        UserAuth userAuth = authRepository.findUserAuthByChatId(chatId);

        if (userAuth != null) {
            authRepository.UpdateVerificationCode(chatId, verificationCode);
            return;
        }

        UserAuth candidate = UserAuth.builder()
                .chatId(chatId)
                .phoneNumber(phoneNumber)
                .verificationCode(verificationCode) //TODO: @FRO: захэшировать
                .authState(AuthState.NOT_AUTHORISED)
                .build();

        authRepository.save(candidate);
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


//        if (candidatesAuth.size()>1){
//            //FIXME: у нас несколько пользователей с одним phoneNumber что-то куда-то сказать
//        } else if (candidatesAuth.size()==1){
        if (candidateAuth.getAuthState() == AuthState.AUTHORISED)
            return true;
        else return false;
    }
//        } else return false;//TODO: как-то сказать что чел тебя нет в вайтлисте
//        return false; //




public  String generateVerificationCode(String chatId){
    Random random = new Random();
    String code = String.format("%04d",random.nextInt(10000));

    authRepository.UpdateVerificationCode(chatId, code);

//    users.get(id).setVerificationCode(code);
    System.out.println(code);

    return code;
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