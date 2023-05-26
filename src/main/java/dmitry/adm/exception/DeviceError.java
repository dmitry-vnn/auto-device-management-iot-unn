package dmitry.adm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceError implements ApiError {


    WRONG_PASSWORD(1, "Wrong password", 400),
    NOT_FOUND(2, "Device not found", 404);

    private final int code;
    private final String message;
    private final int httpStatusCode;

}
