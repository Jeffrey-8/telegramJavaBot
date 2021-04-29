package servises;

import models.UserVacation;

public class MessageService {

    public String getUserInfoMessage(UserVacation vacationInfo) {
        if (!vacationInfo.equals(null)) {
            StringBuffer msg = new StringBuffer();
            msg.append("Информация о сотруднике: " + "\n");
            msg.append("ФИО: " + vacationInfo.getLastName() + vacationInfo.getFirstName() + "\n");
            msg.append("Номер телефона: " + vacationInfo.getPhoneNumber() + "\n");
            msg.append("Должность: " + vacationInfo.getPosition() + "\n");
            msg.append("Расписание отпуска: " + vacationInfo.getVacationDates() + "\n");
            return msg.toString();
        }
        return "Сотрудник не найден";
    }
}
