package dmitry.adm.entity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor

@Entity
@Table(name = "group_device")
@IdClass(GroupDevice.PrimaryKey.class)
public class GroupDevice {

    @EqualsAndHashCode
    static final class PrimaryKey implements Serializable {

        private int groupId;
        private int deviceId;
    }

    @Id
    @Column(name = "group_id")
    private int groupId;

    @Id
    @Column(name = "device_id")
    private int deviceId;

    @Column(name = "device_description")
    private String deviceDescription;

    protected GroupDevice() {
    }
}
