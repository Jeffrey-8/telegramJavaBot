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
@Table(name = "Auth")
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String telegramId;

    private String phoneNumber;

}
