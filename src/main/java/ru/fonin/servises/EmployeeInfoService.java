package ru.fonin.servises;

import ru.fonin.models.UserVacation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeInfoService {
    public Map<String, List<UserVacation>> searchResults = new HashMap<>();

    public String getUserInfoMessage(UserVacation employee) {
        StringBuffer msg = new StringBuffer();
        if (employee != null) {
            msg.append("Информация о сотруднике: " + "\n");
            msg.append("ФИО: " + employee.getLastName() + " " + employee.getFirstName() + "\n");
            msg.append("Номер телефона: " + employee.getPhoneNumber() + "\n");
            msg.append("Должность: " + employee.getPosition() + "\n");
            msg.append("Расписание отпуска: " + employee.getVacationDates() + "\n");
        } else
            msg.append("По вашему запросу ничего не найдено");
        return msg.toString();
    }

    public String getUsersListInfoMessage(List<UserVacation> employeeInfos) {
        StringBuffer msg = new StringBuffer();
        if (employeeInfos != null) {
            msg.append("По вашему запросу найдены следующие сотрудники: " + "\n");
            int count = 1;
            for (UserVacation employee : employeeInfos) {
                msg.append(count + ". " + employee.getLastName() + " " + employee.getFirstName() + "\n");
                count++;
            }
            msg.append("Пожалуйста, выберите номер сотрудника." + "\n");
        } else
            msg.append("По вашему запросу ничего не найдено");
        return msg.toString();
    }
    public String getUsersListInfoMessageByFullName(List<UserVacation> employeeInfos){
        StringBuffer msg = new StringBuffer();

        return msg.toString();
    }

    public boolean isNameCorrect(String name) {
//        return name.matches("^[A-ЯЁ][а-яё]+\\s[A-ЯЁ][а-яё]+$");
        return true; // FIXME заглушка
    }

    public String[] splitLastNameFirstName(String message) {
        String[] result = message.split(" ");
        return result;
    }

    public boolean isNumberCorrect(String string) {
        try {
            int i = Integer.parseInt(string);
            return (i > 0);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isMsgInitials(String message){
        return false;
    }
    public Map<String, List<UserVacation>> getSearchResults() {
        return searchResults;
    }
}
