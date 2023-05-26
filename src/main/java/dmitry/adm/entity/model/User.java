package dmitry.adm.entity.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "user")

@Getter

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @Column(name = "telegram_id")
    private int telegramId;

    @Column(name = "full_name")
    private String fullName;


    protected User() {}

    public User(int telegramId, String fullName) {
        this.telegramId = telegramId;
        this.fullName = fullName;
    }
}
