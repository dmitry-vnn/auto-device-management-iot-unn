package dmitry.adm.entity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "device")

@Getter
@AllArgsConstructor
public class Device {

    @Id
    private int id;

    private String password;

    protected Device() {

    }
}
