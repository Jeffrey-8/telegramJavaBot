package models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Vacation")
public class UserVacation {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String telegramId;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String position;

    private String vacationDates;

    @Enumerated( value = EnumType.STRING)
    private Role role;
    @Enumerated( value = EnumType.STRING)
    private State state;
}
