package ru.fonin.repositories;

import ru.fonin.models.UserVacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserVacationRepository extends JpaRepository<UserVacation,  Long> {
// UserVacation findUserVacationByPhoneNumber(String phoneNumber);
// UserVacation findAllByPhoneNumber(String phoneNumber);

 List<UserVacation> findAllByLastName(String lastName);
 List<UserVacation> findAllByFullNameContains(String fullName);

 List<UserVacation>  findAllByLastNameAndFirstName(String lastName, String FirstName);

// List<UserVacation> findAllByLastNameAndFirstNameOrLastName(String lastName, String FirstName);

}
