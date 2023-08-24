package dmitry.adm.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "`group`")

@Getter
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "owner_id")
    private int ownerId;

    @Setter
    private String password;

    public Group(String name, int ownerId, String password) {
        this.name = name;
        this.ownerId = ownerId;
        this.password = password;
    }

    protected Group() {
    }
}
