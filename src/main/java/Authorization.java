import java.lang.reflect.Array;
import java.util.*;

public class Authorization {
private static  Map<String,User> users = new HashMap<>();
public static void setUpEmployees(){

    users.put("544215456",new User("544215456","+79197789484"));
    users.put("482368686",new User("482368686","+79197789483"));
    users.get("482368686").setLoggedIn(true);
}

    public  static  void addUser(String id){
        users.put(id,new User(id,""));
    }

    public  static  void authoriseUser(String id){
        users.get(id).setLoggedIn(true);
    }
    public  static boolean isUserAuthorised(String id){
    return users.containsKey(id) && users.get(id).isLoggedIn();
}

public static String generateVerificationCode(String id){
    Random random = new Random();
    String code = String.format("%04d",random.nextInt(10000));
    users.get(id).setVerificationCode(code);
    return code;
}
public static boolean verifyCode(String id, String code){
    return  code.equals(users.get(id).getVerificationCode());
}

}

class  User {
    private String id;
    private String phoneNumber;
    private String verificationCode;
    private boolean loggedIn = false;

    public User(String id, String phoneNumber) {
        this.id = id;
        this.phoneNumber = phoneNumber;
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