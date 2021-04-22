import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Authorization {
private static  List<Employee> employees = new ArrayList<>();
public static void setUpEmployees(){
    employees.add(new Employee("544215456","+79197789483",null,null));
}

public  static boolean isUserAuthorised(String id){
    for(Employee employee: employees){
        if (employee.id.equals(id))
            return true;
    }
    return  false;
}
public  static  void addUser(String id){
    employees.add(new Employee(id,"",null,null));
}

public static String generateVerificationCode(){
    Random random = new Random();
    int num = random.nextInt(10000);
    return String.format("%04d",num);
}

}
class Employee{

    String id;
    String phoneNumber;
    Date   vacationStartDate;
    Date   vacationEndDate;

    public Employee(String id, String phoneNumber, Date vacationStartDate, Date vacationEndDate) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.vacationStartDate = vacationStartDate;
        this.vacationEndDate = vacationEndDate;
    }
}

class  User extends Employee{

    private String verificationCode;

    public User(String id, String phoneNumber, Date vacationStartDate, Date vacationEndDate) {
        super(id, phoneNumber, vacationStartDate, vacationEndDate);
    }
}