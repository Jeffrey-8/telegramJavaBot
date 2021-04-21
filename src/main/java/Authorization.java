import java.lang.reflect.Array;
import java.util.Date;
import java.util.Random;

public class Authorization {
private static final Employee[] employees = { new Employee("482368687","+79197789483",null,null)};

public  static boolean isUserAuthorised(String id){
    for(Employee employee: employees){
        if (employee.id.equals(id))
            return true;
    }
    return  false;
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