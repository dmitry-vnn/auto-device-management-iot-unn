package dmitry.adm.entity.dto;

import dmitry.adm.error.ApiError;
import lombok.Getter;

@Getter
public class ResponseError {

    private final int errorCode;
    private final String message;

    public ResponseError(ApiError apiError) {
        this.errorCode = apiError.getCode();
        this.message = apiError.getMessage();
    }

}
