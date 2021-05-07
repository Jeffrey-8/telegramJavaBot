package repositories;


import models.AuthState;
import models.State;
import models.UserAuth;
import models.UserVacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
public interface AuthRepository extends JpaRepository<UserAuth,  Long> {
    List<UserAuth> findAllByPhoneNumber(String phoneNumber);
//    UserAuth findOneByChatId(String chatId);
    UserAuth findUserAuthByChatId(String chatId);
    UserAuth findUserAuthByPhoneNumber(String phoneNumber);

    @Modifying
    @Transactional
    @Query("update UserAuth u set u.authState=?2 where u.chatId=?1")
    void UpdateState(String chatId,AuthState state);

    @Modifying
    @Transactional
    @Query("update UserAuth u set u.authState=?2,u.verificationCode=?3,u.chatId=?4 where u.phoneNumber=?1")
    void UpdateUserAuth(String phoneNumber,AuthState state,String verificationCode,String chatId);


    @Modifying
    @Transactional
    @Query("update UserAuth u set u.verificationCode=?2 where u.phoneNumber=?1")
    void UpdateVerificationCode(String phoneNumber,String verificationCode);



//    @Query("update User u set u.firstname = ?1, u.lastname = ?2 where u.id = ?3")

}
