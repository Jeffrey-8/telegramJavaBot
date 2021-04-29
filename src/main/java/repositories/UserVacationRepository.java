package repositories;

import models.UserVacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVacationRepository extends JpaRepository<UserVacation, Long> {
 UserVacation findUserVacationByPhoneNumber(String phoneNumber);
// UserVacation findAllByPhoneNumber(String phoneNumber);
 UserVacation findUserVacationByLastName(String lastName);

}
