package dmitry.adm.error;

import lombok.Getter;

@Getter
public class ApiErrorException extends RuntimeException {

    private final ApiError apiError;

    public ApiErrorException(ApiError apiError) {
        super(apiError.getMessage());

        this.apiError = apiError;
    }
}
