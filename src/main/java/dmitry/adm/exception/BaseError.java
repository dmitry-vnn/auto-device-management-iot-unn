package dmitry.adm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseError implements ApiError {

    NOT_ALL_ARGS_REPRESENT(0, "one of the params is missing", 400);

    private final int code;
    private final String message;
    private final int httpStatusCode;
}
