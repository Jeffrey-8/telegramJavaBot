package test.RepositoryTest;

import models.Role;
import models.State;
import models.UserVacation;
import org.springframework.beans.factory.annotation.Autowired;
import repositories.UserVacationRepository;

public class RepositoryTest {
    @Autowired
    UserVacationRepository repository;

    public UserVacation findUserTest(String phoneNumber){
        return repository.findUserVacationByPhoneNumber(phoneNumber);
    }

    public void saveTest(){
//        UserVacation userVacation1=
//           UserVacation.builder()
//                   .phoneNumber("9991232323")
//                .telegramId("@fonin")
//                .vacationDates("27.05.21 - 29.05.21")
//                .role(Role.USER)
//                .state(State.AT_WORK).build();
//
//
//
//
//        repository.save(userVacation1);
    }

}
