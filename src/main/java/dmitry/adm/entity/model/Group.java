package dmitry.adm.entity.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "group")

@Getter
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String name;

    @Column(name = "owner_id")
    private int ownerId;

    private String password;



}
