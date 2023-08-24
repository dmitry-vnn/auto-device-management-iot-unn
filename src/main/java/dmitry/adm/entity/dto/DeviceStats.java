package dmitry.adm.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DeviceStats {

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime time;

    private Integer value;

}
