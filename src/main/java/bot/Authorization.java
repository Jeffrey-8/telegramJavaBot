package bot;

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


    private   Map<String,User> users = new HashMap<>();

    public void setUpEmployees() {
        List<UserAuth> usersQuerry = authRepository.findAll();
        for (UserAuth user : usersQuerry) {
            users.put(user.getTelegramId(), new User(user.getTelegramId(), user.getPhoneNumber(), user.isLoggedIn()));
        }
    }

    public  void addUser(String id) {
        users.put(id, new User(id, "", false));
    }

    public    void authoriseUser(String id){
        users.get(id).setLoggedIn(true);
    }

    public  boolean isUserAuthorised(String id) {
        // this wil be the way to do it
//    UserAuth user = authRepository.findUserAuthById(id);
//    if(user != null && user.isLoggedIn())
//        return true;
//    return false;
        return users.containsKey(id) && users.get(id).isLoggedIn();
    }

public  String generateVerificationCode(String id){
    Random random = new Random();
    String code = String.format("%04d",random.nextInt(10000));
    users.get(id).setVerificationCode(code);
    return code;
}
public  boolean verifyCode(String id, String code){
//    return  code.equals(users.get(id).getVerificationCode());
return  true; //FIXME: temporary auth plug
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