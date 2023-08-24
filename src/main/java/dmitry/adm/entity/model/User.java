package dmitry.adm.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "member")

@Getter

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "telegram_id")
    private int telegramId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "active_group_id")
    @Setter
    private Integer activeGroupId;

    protected User() {}

    public User(int telegramId, String fullName) {
        this.telegramId = telegramId;
        this.fullName = fullName;
    }
}
