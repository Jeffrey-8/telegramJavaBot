package servises;

import models.UserVacation;
import org.apache.catalina.User;
import org.telegram.telegrambots.meta.api.objects.Contact;

import java.util.List;

public class MessageService {
    public String getUserInfoMessage(List<UserVacation> employeeInfos) {
        if (employeeInfos.size()==1) {

            UserVacation employee = employeeInfos.get(0);
            StringBuffer msg = new StringBuffer();
            msg.append("Информация о сотруднике: " + "\n");
            msg.append("ФИО: " + employee.getLastName() + " " + employee.getFirstName() + "\n");
            msg.append("Номер телефона: " + employee.getPhoneNumber() + "\n");
            msg.append("Должность: " + employee.getPosition() + "\n");
            msg.append("Расписание отпуска: " + employee.getVacationDates() + "\n");
            return msg.toString();
        } else if (employeeInfos.size()>1){
            StringBuffer msg = new StringBuffer();
            msg.append("Найдено несколько таких сотрудников: " + "\n");
            for ( UserVacation employee: employeeInfos) {
                msg.append("ФИО: " + employee.getLastName() + " " + employee.getFirstName() + "\n");
            }
            msg.append("Уточните пожалуйста ФИО." + "\n");
            return msg.toString();
        }
        return "Сотрудник не найден";
    }
}
