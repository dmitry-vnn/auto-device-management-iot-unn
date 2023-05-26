package dmitry.adm.entity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "device_value")
@Entity
@IdClass(DeviceValue.PrimaryKey.class)

@Getter
@AllArgsConstructor
public class DeviceValue {

    @EqualsAndHashCode
    static final class PrimaryKey implements Serializable {
        private int deviceId;
        private LocalDateTime timeReceive;
    }


    @Id
    @Column(name = "device_id")
    private int deviceId;

    @Id
    @Column(name = "time_receive")
    private LocalDateTime timeReceive;

    private int value;



    protected DeviceValue() {}

    public DeviceValue(int deviceId, int value) {
        this(deviceId, LocalDateTime.now(), value);
    }
}
